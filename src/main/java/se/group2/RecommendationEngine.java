package se.group2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationEngine {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private UserRepository userRepository;

    public User getUserById(int id) {
        return userRepository.findById(id);
    }

    public Movie getMovieById(int id) {
        return movieRepository.findById(id);
    }

    public Movie getMovieByTitle(String title){
        return movieRepository.findByTitle(title).get(0);
    }

    public Rating getRatingByUserAndMovie(int userId, int movieId) {
        return ratingRepository.findRatingByUserIdAndMovieId(userId, movieId);
    }

    public List<Movie> recommendMovies(String genderInput, String ageInput, Integer occupationInput, List<String> genresInput, String titleInput, Integer limitNo) {
        List<Movie> recommendations = new ArrayList<>();

        // recommendation algorithm: weighted average
        // first, divide users in 8 groups, each has same(similar)/different properties with the input for each property.
        // we have 3 properties, so there are 2^3=8 groups
        int[] groupCount = new int[8];
        for (User u: userRepository.findAll()) {
            int ind = (0b100 * (u.getGender().equals(genderInput) ? 1 : 0)) |
                    (0b10 * (u.canBeAge(ageInput) ? 1 : 0)) |
                    (u.getOccupation()==occupationInput ? 1 : 0);
            groupCount[ind] += 1;
        }

        HashMap<Integer, Double> movieRatingSum = new HashMap<>();
        HashMap<Integer, Integer> movieRatingCnt = new HashMap<>();

        for (Movie m: movieRepository.findAll()) {
            movieRatingSum.put(m.id, 0.0);
            movieRatingCnt.put(m.id, 0);
        }

        // weighted rating = {rating} * (e ^ (1 - {number of users in the group}/{total number of users})
        // to give more weight as the group is smaller, less weight as the group is larger.
        // the weight get closer to 1 as the group is larger.

        for (Rating r: ratingRepository.findAll()) {
            User user = getUserById(r.userId);
            double groupRatio = (double)groupCount[
                    (0b100 * (user.getGender().equals(genderInput) ? 1 : 0)) |
                    (0b10 * (user.canBeAge(ageInput) ? 1 : 0)) |
                    (user.getOccupation()==occupationInput ? 1 : 0)] / userRepository.count();
            double weight = Math.exp(1 - groupRatio);
            movieRatingSum.put(r.movieId, movieRatingSum.get(r.movieId) + r.rating * weight);
            movieRatingCnt.put(r.movieId, movieRatingCnt.get(r.movieId) + 1);
        }

        // find average of each movies
        HashMap<Integer, Double> movieRatingAvg = new HashMap<>();
        for (Integer k: movieRatingSum.keySet()) {
            if (movieRatingCnt.get(k) == 0){
                movieRatingAvg.put(k, 0.0);
            } else {
                movieRatingAvg.put(k, movieRatingSum.get(k) / movieRatingCnt.get(k));
            }
        }

        // sort by average score, in reverse order
        List<Map.Entry<Integer, Double>> sortedRatings = movieRatingAvg.entrySet().stream().sorted(
                Map.Entry.<Integer, Double>comparingByValue().reversed()).collect(Collectors.toList());

        // convert titleInput to genresInput (even there are no movies with title)
        List<String> genresTemp = genresInput;
        if(!titleInput.isEmpty()) {
            Movie movieTemp = getMovieByTitle(titleInput);
            if(movieTemp != null)
                genresTemp = movieTemp.genre;
        }

        // put top 10 (or limitNo) movie objects to recommendations
        for (Map.Entry<Integer, Double> movieRating: sortedRatings) {
            if (movieRatingCnt.get(movieRating.getKey()) < 5) {  // if the movie is rated less than 5 times, skip it.
                continue;
            }
            if(genresTemp.size() > 0){
                for(String g: getMovieById(movieRating.getKey()).genre){
                    if(genresTemp.contains(g)){
                        if(titleInput.isEmpty() || !getMovieById(movieRating.getKey()).title.equals(titleInput))
                            recommendations.add(getMovieById(movieRating.getKey()));
                        break;
                    }
                }
            }
            else {
                if(titleInput.isEmpty() || !getMovieById(movieRating.getKey()).title.equals(titleInput))
                    recommendations.add(getMovieById(movieRating.getKey()));
            }
            if (recommendations.size() >= limitNo) {
                break;
            }
        }
        return recommendations;
    }
}
