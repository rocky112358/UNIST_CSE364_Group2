package se.group2;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class MovieblockController {
    private static List<Link> links;

    public static void loadLinks(String filename) {
        List<Link> l = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = br.readLine()) != null) {
                String[] properties = line.split("::");
                Link link = new Link(properties[0], properties[1]);
                l.add(link);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        links = l;
    }

    public static String printMovie(Movie movie) {
        for (Link l: links) {
            if (l.movieId == movie.id) {
                return String.format("http://www.imdb.com/title/tt%s", l.imdbId);
            }
        }
        return null;
    }

    @RequestMapping(value = "/users/recommendations", method = GET, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public List<RecommendationOutput> userRecommendations(@RequestBody UserRecommendationInput input) {
        // TODO: load data on application startup to save time
        boolean isInvalid = false;
        if(!Movieblock.validateAgeInput(input.getAge())){
            System.out.println("Error: Invalid Age Input");
            isInvalid = true;
        }
        if(!isInvalid && !Movieblock.validateGenderInput(input.getGender())){
            System.out.println("Error: Invalid Gender Input");
            isInvalid = true;
        }

        Integer occupationNo = Movieblock.encodeOccupation(input.getOccupation());
        if(!isInvalid && occupationNo == -99) {
            System.out.println("Error: Invalid Occupation Input");
            isInvalid = true;
        }

        List<String> genresInput = new ArrayList<>();
        if (input.getGenres().length() != 0) {
            genresInput = Arrays.asList(input.getGenres().split("\\|"));
            genresInput = genresInput.stream().map(String::toLowerCase).collect(Collectors.toList());
        }

        if(!isInvalid){
            for (String g : genresInput) {
                if (!Movieblock.validateGenreInput(g)) {
                    System.out.println("Error: invalid genre input");
                    isInvalid = true;
                    break;
                }
            }
        }

        if(isInvalid){
            List<RecommendationOutput> recommendationResults = new ArrayList<>();
            return recommendationResults;
        }

        RecommendationEngine engine = new RecommendationEngine();
        List<Movie> recommendationResults = engine.recommendMovies(input.getGender(), input.getAge(), occupationNo, genresInput, "", 10);

        loadLinks("data/links.dat");

        return recommendationResults.stream().map(
                (movie -> new RecommendationOutput(
                        movie.title, String.join("|", movie.genre), printMovie(movie)))
        ).collect(Collectors.toList());
    }

    @RequestMapping(value = "/movies/recommendations", method = GET, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public List<RecommendationOutput> movieRecommendations(@RequestBody MovieRecommendationInput input) {
        // TODO: load data on application startup to save time
        boolean isInvalid = false;
        if(input.getLimit() < 0){
            System.out.println("Error: Invalid Limit Input");
            isInvalid = true;
        }

        RecommendationEngine engine = new RecommendationEngine();
        if(!isInvalid && engine.getMovieByTitle(input.getTitle()) == null){
            System.out.println("Error: Invalid Title Input");
            isInvalid = true;
        }

        if(isInvalid){
            List<RecommendationOutput> recommendationResults = new ArrayList<>();
            return recommendationResults;
        }
        List<String> genresInput = new ArrayList<>();
        List<Movie> recommendationResults = engine.recommendMovies("", "", -1, genresInput, input.getTitle(), input.getLimit());

        loadLinks("data/links.dat");

        return recommendationResults.stream().map(
                (movie -> new RecommendationOutput(
                        movie.title, String.join("|", movie.genre), printMovie(movie)))
        ).collect(Collectors.toList());
    }
}
