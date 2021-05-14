package se.group2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class RecommendationEngine {
    private static List<Movie> movies;
    private static List<Rating> ratings;
    private static List<User> users;

    public RecommendationEngine(){
        movies = new ArrayList<>();
        ratings = new ArrayList<>();
        users = new ArrayList<>();
    }

    public User getUserById(int id) {
        for (User u: users) {
            if (u.id == id) {
                return u;
            }
        }
        return null;
    }

    public Movie getMovieById(int id) {
        for (Movie m: movies) {
            if (m.id == id) {
                return m;
            }
        }
        return null;
    }

    public Rating getRatingByUserAndMovie(int userId, int movieId) {
        for (Rating r: ratings) {
            if (r.userId == userId && r.movieId == movieId) {
                return r;
            }
        }
        return null;
    }

    public void loadMovies(String filename) {
        List<Movie> m = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = br.readLine()) != null) {
                String[] properties = line.split("::");
                Movie movie = new Movie(properties[0], properties[1], properties[2].toLowerCase());
                m.add(movie);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.printf("[ERROR] Could not load movie data from '%s'", filename);
            System.exit(-1);
        }
        movies = m;
    }

    public void loadUsers(String filename) {
        List<User> u = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = br.readLine()) != null) {
                String[] properties = line.split("::");
                User user = new User(properties[0], properties[1].toLowerCase(), properties[2], properties[3], properties[4]);
                u.add(user);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.printf("[ERROR] Could not load user data from '%s'", filename);
            System.exit(-1);
        }
        users = u;
    }

    public void loadRatings(String filename) {
        List<Rating> r = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = br.readLine()) != null) {
                String[] properties = line.split("::");
                Rating rating = new Rating(properties[0], properties[1], properties[2], properties[3]);
                r.add(rating);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.printf("[ERROR] Could not load rating data from '%s'", filename);
            System.exit(-1);
        }
        ratings = r;
    }

    public List<Movie> recommendMovies(String genderInput, String ageInput, Integer occupationInput, List<String> genresInput, Integer limitNo) {
        List<Movie> recommendations = new ArrayList<>();

        if (movies.size() == 0) {
            this.loadMovies("data/movies.dat");
        }
        if (ratings.size() == 0) {
            this.loadRatings("data/ratings.dat");
        }
        if (users.size() == 0) {
            this.loadUsers("data/users.dat");
        }

        // recommendation algorithm: weighted average
        // first, divide users in 8 groups, each has same(similar)/different properties with the input for each property.
        // we have 3 properties, so there are 2^3=8 groups
        int[] groupCount = new int[8];
        for (User u: users) {
            int ind = (0b100 * (u.gender.equals(genderInput) ? 1 : 0)) |
                    (0b10 * (u.canBeAge(ageInput) ? 1 : 0)) |
                    (u.occupation==occupationInput ? 1 : 0);
            groupCount[ind] += 1;
        }

        HashMap<Integer, Double> movieRatingSum = new HashMap<>();
        HashMap<Integer, Integer> movieRatingCnt = new HashMap<>();

        for (Movie m: movies) {
            movieRatingSum.put(m.id, 0.0);
            movieRatingCnt.put(m.id, 0);
        }

        // weighted rating = {rating} * (e ^ (1 - {number of users in the group}/{total number of users})
        // to give more weight as the group is smaller, less weight as the group is larger.
        // the weight get closer to 1 as the group is larger.

        for (Rating r: ratings) {
            User user = getUserById(r.userId);
            double groupRatio = (double)groupCount[
                    (0b100 * (user.gender.equals(genderInput) ? 1 : 0)) |
                    (0b10 * (user.canBeAge(ageInput) ? 1 : 0)) |
                    (user.occupation==occupationInput ? 1 : 0)] / users.size();
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

        // put top 10 movie objects to recommendations
        for (Map.Entry<Integer, Double> movieRating: sortedRatings) {
            if (movieRatingCnt.get(movieRating.getKey()) < 5) {  // if the movie is rated less than 5 times, skip it.
                continue;
            }
            if(genresInput.size() > 0){
                for(String g: getMovieById(movieRating.getKey()).genre){
                    if(genresInput.contains(g)){
                        recommendations.add(getMovieById(movieRating.getKey()));
                        break;
                    }
                }
            }
            else {
                recommendations.add(getMovieById(movieRating.getKey()));
            }
            if (recommendations.size() >= limitNo) {
                break;
            }
        }
        return recommendations;
    }
}
