package se.group2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

class Movie {
    int id;
    String title;
    List<String> genre;

    public Movie(String i, String t, String g) {
        id = Integer.parseInt(i);
        title = t;
        genre = Arrays.asList(g.split("\\|"));
    }
}

class User {
    int id;
    String gender;
    int age;
    int occupation;
    String zipcode;

    public User(String i, String g, String a, String o, String z) {
        id = Integer.parseInt(i);
        gender = g;
        age = Integer.parseInt(a);
        occupation = Integer.parseInt(o);
        zipcode = z;
    }
}

class Rating {
    int userId;
    int movieId;
    int rating;
    int timestamp;

    public Rating(String u, String m, String r, String t) {
        userId = Integer.parseInt(u);
        movieId = Integer.parseInt(m);
        rating = Integer.parseInt(r);
        timestamp = Integer.parseInt(t);
    }
}

class Link {
    int movieId;
    String imdbId;

    public Link(String m, String i) {
        movieId = Integer.parseInt(m);
        imdbId = i;

    }
}

public class Movieblock {
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

    public static void printMovie(Movie movie) {
        for (Link l: links) {
            if (l.movieId == movie.id) {
                System.out.printf("%s (http://www.imdb.com/title/tt%s)\n", movie.title, l.imdbId);
                break;
            }
        }
    }

    public static void main(String[] args) {
        if (args.length < 3 || args.length > 4) {
            System.out.println("Args: gender age occupation [genre(s)]");
            System.exit(-1);
        }
        String genderInput = "";
        String ageInput = "";
        String occupationInput = "";
        List<String> genresInput = new ArrayList<String>();
        genderInput = args[0];
        ageInput = args[1];
        occupationInput = args[2];
        if (args.length == 4) {
            genresInput = Arrays.asList(args[3].split("\\|"));
        }

        // validate gender input
        List<String> genderCandidate = new ArrayList<String>();
        genderCandidate.add("F");
        genderCandidate.add("M");
        genderCandidate.add("");
        if (!genderCandidate.contains(genderInput)) {
            System.out.println("Error: invalid gender input");
            System.exit(-1);
        }

        // validate age input
        if (!ageInput.equals("") && Integer.parseInt(ageInput) < 0) {
            System.out.println("Error: invalid age input");
            System.exit(-1);
        }

        // validate occupation input and convert to integer
        Map<String, Integer> occupationMap = new HashMap<>();
        occupationMap.put("", -1);
        occupationMap.put("other", 0);
        occupationMap.put("academic", 1);
        occupationMap.put("educator", 1);
        occupationMap.put("artist", 2);
        occupationMap.put("clerical", 3);
        occupationMap.put("admin", 3);
        occupationMap.put("collegestudent", 4);
        occupationMap.put("college student", 4);
        occupationMap.put("gradstudent", 4);
        occupationMap.put("grad student", 4);
        occupationMap.put("customerservice", 5);
        occupationMap.put("customer service", 5);
        occupationMap.put("doctor", 6);
        occupationMap.put("healthcare", 6);
        occupationMap.put("health care", 6);
        occupationMap.put("executive", 7);
        occupationMap.put("managerial", 7);
        occupationMap.put("farmer", 8);
        occupationMap.put("homemaker", 9);
        occupationMap.put("k-12student", 10);
        occupationMap.put("k-12 student", 10);
        occupationMap.put("lawyer", 11);
        occupationMap.put("programmer", 12);
        occupationMap.put("retired", 13);
        occupationMap.put("sales", 14);
        occupationMap.put("marketing", 14);
        occupationMap.put("scientist", 15);
        occupationMap.put("self-employed", 16);
        occupationMap.put("technician", 17);
        occupationMap.put("engineer", 17);
        occupationMap.put("tradesman", 18);
        occupationMap.put("craftsman", 18);
        occupationMap.put("unemployed", 19);
        occupationMap.put("writer", 20);

        Integer occupationInputNo = occupationMap.get(occupationInput.toLowerCase());
        if (occupationInputNo == null) {
            System.out.println("Error: invalid occupation input");
            System.exit(-1);
        }

        // if there are genres input, validate
        String[] genreArray = {"action", "adventure", "animation", "children's", "comedy", "crime", "documentary", "drama", "fantasy", "film-noir", "horror", "musical", "mystery", "romance", "sci-fi", "thriller", "war", "western"};
        if (args.length == 4) {
            genresInput = genresInput.stream().map(String::toLowerCase).collect(Collectors.toList());
            for (String g : genresInput) {
                if (!g.equals("") && !Arrays.asList(genreArray).contains(g)) {
                    System.out.println("Error: invalid genre input");
                    System.exit(-1);
                }
            }
        }

        // prepare engine
        RecommendationEngine engine;
        engine = new RecommendationEngine();

        // run
        List<Movie> recommendations;
        recommendations = engine.recommendMovies(genderInput, ageInput, occupationInput);

        // print result
        loadLinks("data/links.dat");
        for (Movie m: recommendations) {
            printMovie(m);
        }
    }
}
