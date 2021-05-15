package se.group2;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class MovieblockController {

    @RequestMapping(value = "/users/recommendations", method = POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public List<RecommendationOutput> recommendations(@RequestBody RecommendationInput input) {
        // TODO: load data on application startup to save time
        // TODO: validate each input
        // TODO: validate input pair
        // ex) if any of gender, age, occupation is present, title and limit should not exist.
        // also for opposite situation
        Integer occupationNo = Movieblock.encodeOccupation(input.getOccupation());

        List<String> genresInput = new ArrayList<>();
        if (input.getGenres().length() != 0) {
            genresInput = Arrays.asList(input.getGenres().split("\\|"));
            genresInput = genresInput.stream().map(String::toLowerCase).collect(Collectors.toList());
        }

        RecommendationEngine engine = new RecommendationEngine();
        List<Movie> recommendationResults = engine.recommendMovies(input.getGender(), input.getAge(), occupationNo, genresInput, input.getTitle(), input.getLimit());

        // TODO: get imdb id from links.dat file and construct imdb url
        return recommendationResults.stream().map(
                (movie -> new RecommendationOutput(
                        movie.title, String.join("|", movie.genre), "https://naver.com"))
        ).collect(Collectors.toList());
    }

}
