package se.group2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@Service
public class MovieblockController {
    private static List<Link> links;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private LinkRepository linkRepository;
    @Autowired
    private MoviePosterRepository moviePosterRepository;
    @Autowired
    RecommendationEngine recommendationEngine;

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

    public String printMovie(Movie movie) {
        Link l = linkRepository.findByMovieId(movie.id);
        if (l != null) {
            return String.format("http://www.imdb.com/title/tt%s", l.imdbId);
        }
        return null;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/movies")
    public List<Movie> allMovies() {
        return movieRepository.findAll();
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/links")
    public List<Link> allLinks() {
        return linkRepository.findAll();
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/posters")
    public MoviePoster getMoviePoster(@RequestParam Integer id) {
        return moviePosterRepository.findByMovieId(id);
    }

    @CrossOrigin
    @RequestMapping(value = "/users/recommendations", method = POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
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

        List<Movie> recommendationResults = recommendationEngine.recommendMovies(input.getGender(), input.getAge(), occupationNo, genresInput, "", 10);

        return recommendationResults.stream().map(
                (movie -> new RecommendationOutput(
                        movie.id, movie.title, String.join("|", movie.genre), printMovie(movie)))
        ).collect(Collectors.toList());
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/movies/recommendations", method = POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public List<RecommendationOutput> movieRecommendations(@RequestBody MovieRecommendationInput input) throws InvalidInputException {
        // TODO: load data on application startup to save time
        if (input.getTitle() == null) {
            throw new InvalidInputException("Error: Title input not given");
        }

        if(!Movieblock.validateLimitInput(input.getLimit())) {
            throw new InvalidInputException("Error: Invalid Limit Input (must be an positive integer)");
        }

        Integer limit = Integer.parseInt(input.getLimit());

        if(recommendationEngine.getMovieByTitle(input.getTitle()) == null){
            throw new InvalidInputException("Error: Movie does not exist");
        }

        List<String> genresInput = new ArrayList<>();
        List<Movie> recommendationResults = recommendationEngine.recommendMovies("", "", -1, genresInput, input.getTitle(), limit);

        return recommendationResults.stream().map(
                (movie -> new RecommendationOutput(
                        movie.id, movie.title, String.join("|", movie.genre), printMovie(movie)))
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
