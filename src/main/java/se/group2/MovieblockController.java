package se.group2;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
                Link link = new Link(Integer.parseInt(properties[0]), properties[1]);
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
    public List<RecommendationOutput> userRecommendations(@RequestBody UserRecommendationInput input) throws InvalidInputException {
        // TODO: load data on application startup to save time
        if (input.getAge() == null) {
            throw new InvalidInputException("Error: Age input is not given");
        }

        if(!Movieblock.validateAgeInput(input.getAge())){
            throw new InvalidInputException("Error: Invalid Age Input");
        }

        if (input.getGender() == null) {
            throw new InvalidInputException("Error: Gender input is not given");
        }

        if(!Movieblock.validateGenderInput(input.getGender())){
            throw new InvalidInputException("Error: Invalid Gender Input");
        }

        if (input.getOccupation() == null) {
            throw new InvalidInputException("Error: Occupation input is not given");
        }

        Integer occupationNo = Movieblock.encodeOccupation(input.getOccupation());
        if(occupationNo == -99) {
            throw new InvalidInputException("Error: Invalid Occupation Input");
        }

        List<String> genresInput = new ArrayList<>();
        if (input.getGenres() == null) {
            throw new InvalidInputException("Error: Genres input is not given");
        }

        if (input.getGenres().length() != 0) {
            genresInput = Arrays.asList(input.getGenres().split("\\|"));
            genresInput = genresInput.stream().map(String::toLowerCase).collect(Collectors.toList());
        }

        for (String g : genresInput) {
            if (!Movieblock.validateGenreInput(g)) {
                throw new InvalidInputException("Error: invalid genres input");
            }
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
    public List<RecommendationOutput> movieRecommendations(@RequestBody MovieRecommendationInput input) throws InvalidInputException {
        // TODO: load data on application startup to save time
        if (input.getTitle() == null) {
            throw new InvalidInputException("Error: Title input not given");
        }

        if(!Movieblock.validateLimitInput(input.getLimit())) {
            throw new InvalidInputException("Error: Invalid Limit Input (must be an positive integer)");
        }

        Integer limit = Integer.parseInt(input.getLimit());

        RecommendationEngine engine = new RecommendationEngine();
        if(engine.getMovieByTitle(input.getTitle()) == null){
            throw new InvalidInputException("Error: Movie does not exist");
        }

        List<String> genresInput = new ArrayList<>();
        List<Movie> recommendationResults = engine.recommendMovies("", "", -1, genresInput, input.getTitle(), limit);

        loadLinks("data/links.dat");

        return recommendationResults.stream().map(
                (movie -> new RecommendationOutput(
                        movie.title, String.join("|", movie.genre), printMovie(movie)))
        ).collect(Collectors.toList());
    }

    @ExceptionHandler(NumberFormatException.class)
    public Object invalidLimitInput(NumberFormatException e) {
        return new ResponseEntity<>(
                new ApiError(400, "invalid input", "Limit input should be an integer value"),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Object invalidHttpMessage(HttpMessageNotReadableException e) {
        return new ResponseEntity<>(
                new ApiError(400, "invalid input", "Illegal JSON input. Please check your input syntax again."),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public Object invalidInput(Exception e) {
        return new ResponseEntity<>(
                new ApiError(400, "invalid input", e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }
}
