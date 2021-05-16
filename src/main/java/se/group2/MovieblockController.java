package se.group2;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class MovieblockController {

    @RequestMapping(value = "/users/recommendations", method = GET, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public List<RecommendationOutput> userRecommendations(@RequestBody UserRecommendationInput input) {
        // TODO: load data on application startup to save time
        // TODO: validate each input
        Integer occupationNo = Movieblock.encodeOccupation(input.getOccupation());

        List<String> genresInput = new ArrayList<>();
        if (input.getGenres().length() != 0) {
            genresInput = Arrays.asList(input.getGenres().split("\\|"));
            genresInput = genresInput.stream().map(String::toLowerCase).collect(Collectors.toList());
        }

        RecommendationEngine engine = new RecommendationEngine();
        List<Movie> recommendationResults = engine.recommendMovies(input.getGender(), input.getAge(), occupationNo, genresInput, "", 10);

        // TODO: get imdb id from links.dat file and construct imdb url
        return recommendationResults.stream().map(
                (movie -> new RecommendationOutput(
                        movie.title, String.join("|", movie.genre), "https://naver.com"))
        ).collect(Collectors.toList());
    }

    @RequestMapping(value = "/movies/recommendations", method = GET, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public List<RecommendationOutput> movieRecommendations(@RequestBody MovieRecommendationInput input) {
        // TODO: load data on application startup to save time
        // TODO: validate each input

        RecommendationEngine engine = new RecommendationEngine();
        List<String> genresInput = new ArrayList<>();
        List<Movie> recommendationResults = engine.recommendMovies("", "", -1, genresInput, input.getTitle(), input.getLimit());

        // TODO: get imdb id from links.dat file and construct imdb url
        return recommendationResults.stream().map(
                (movie -> new RecommendationOutput(
                        movie.title, String.join("|", movie.genre), "https://naver.com"))
        ).collect(Collectors.toList());
    }

}
