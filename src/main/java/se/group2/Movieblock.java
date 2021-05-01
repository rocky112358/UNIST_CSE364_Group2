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

    public boolean canBeAge(String a) {
        if (a.equals("")) {
            return false;
        } else if (age == 1) {
            return Integer.parseInt(a) < 18;
        } else if (age == 18) {
            return 18 <= Integer.parseInt(a) && Integer.parseInt(a) <= 24;
        } else if (age == 25) {
            return 25 <= Integer.parseInt(a) && Integer.parseInt(a) <= 34;
        } else if (age == 35) {
            return 35 <= Integer.parseInt(a) && Integer.parseInt(a) <= 44;
        } else if (age == 45) {
            return 45 <= Integer.parseInt(a) && Integer.parseInt(a) <= 49;
        } else if (age == 50) {
            return 50 <= Integer.parseInt(a) && Integer.parseInt(a) <= 55;
        } else if (age == 56) {
            return 56 <= Integer.parseInt(a);
        } else {
            return false;
        }
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

    public Link getLinkByMovieId(int id) {
        for (Link l: links) {
            return l;
        }
        return null;
    }

    public static boolean validateGenderInput(String genderInput) {
        String[] genderCandidate = {"F", "M", ""};
        return Arrays.asList(genderCandidate).contains(genderInput);
    }

    public static boolean validateAgeInput(String ageInput) {
        return ageInput.equals("") || Integer.parseInt(ageInput) >= 0;
    }

    public static boolean validateOccupationInput(String occupationInput, Map<String, Integer> occupationMap) {
        return occupationMap.get(occupationInput.toLowerCase()) != null;
    }

    public static boolean validateGenreInput(String genreInput) {
        String[] genreArray = {"action", "adventure", "animation", "children's", "comedy", "crime", "documentary",
                "drama", "fantasy", "film-noir", "horror", "musical", "mystery", "romance", "sci-fi", "thriller",
                "war", "western"};
        return genreInput.equals("") || Arrays.asList(genreArray).contains(genreInput);
    }

    public static Integer encodeOccupation(String occupationInput, Map<String, Integer> occupationMap) {
        return occupationMap.get(occupationInput.toLowerCase());
    }

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

        String genderInput = args[0];
        String ageInput = args[1];
        String occupationInput = args[2];
        List<String> genresInput = new ArrayList<>();
        if (args.length == 4) {
            genresInput = Arrays.asList(args[3].split("\\|"));
            if(args[3].length() == 0 || genresInput.size() == 0){
                System.out.println("Error: invalid genre input");
                System.exit(-1);
            }
        }

        // validate gender input
        if (!validateGenderInput(genderInput)) {
            System.out.println("Error: invalid gender input");
            System.exit(-1);
        }

        // validate age input
        if (!validateAgeInput(ageInput)) {
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

        if (!validateOccupationInput(occupationInput, occupationMap)) {
            System.out.println("Error: invalid occupation input");
            System.exit(-1);
        }
        Integer occupationInputNo = encodeOccupation(occupationInput, occupationMap);

        // if there are genres input, validate
        if (args.length == 4) {
            genresInput = genresInput.stream().map(String::toLowerCase).collect(Collectors.toList());
            for (String g : genresInput) {
                if (!validateGenreInput(g)) {
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
        recommendations = engine.recommendMovies(genderInput, ageInput, occupationInputNo, genresInput);

        // print result
        loadLinks("data/links.dat");
        for (Movie m: recommendations) {
            printMovie(m);
        }
    }
}
