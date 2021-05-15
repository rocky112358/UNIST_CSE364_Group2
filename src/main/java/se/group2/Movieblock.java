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
        String[] genderCandidate = {"f", "m", ""};
        return Arrays.asList(genderCandidate).contains(genderInput.toLowerCase());
    }

    public static boolean validateAgeInput(String ageInput) {
        try {
            return ageInput.equals("") || Integer.parseInt(ageInput) >= 0;
        } catch (Exception e) {
            return false;
        }
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

    /*public static boolean validateTitleInput(String titleInput) {
        try{
            return titleInput.equals("")
        }
    }*/

    public static boolean validateLimitInput(String limitInput) {
        try {
            return limitInput.equals("") || Integer.parseInt(limitInput) > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public static Integer encodeOccupation(String occupationInput) {
        switch (occupationInput.toLowerCase()) {
            case "":
                return -1;
            case "other":
                return 0;
            case "academic":
            case "educator":
                return 1;
            case "artist":
                return 2;
            case "clerical":
            case "admin":
                return 3;
            case "collegestudent":
            case "college student":
            case "gradstudent":
            case "grad student":
                return 4;
            case "customerservice":
            case "customer service":
                return 5;
            case "doctor":
            case "healthcare":
            case "health care":
                return 6;
            case "executive":
            case "managerial":
                return 7;
            case "farmer":
                return 8;
            case "homemaker":
                return 9;
            case "k-12student":
            case "k-12 student":
                return 10;
            case "lawyer":
                return 11;
            case "programmer":
                return 12;
            case "retired":
                return 13;
            case "sales":
            case "marketing":
                return 14;
            case "scientist":
                return 15;
            case "self-employed":
                return 16;
            case "technician":
            case "engineer":
                return 17;
            case "tradesman":
            case "craftsman":
                return 18;
            case "unemployed":
                return 19;
            case "writer":
                return 20;
            default:
                return -99;
        }
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
        if (args.length != 6) {
            System.out.println("Args: gender age occupation genre(s) title limit");
            return;
        }

        String genderInput = args[0];
        String ageInput = args[1];
        String occupationInput = args[2];
        List<String> genresInput = new ArrayList<>();
        if (args[3].length() != 0) {
            genresInput = Arrays.asList(args[3].split("\\|"));
        }
        String titleInput = args[4];
        String limitInput = args[5];

        // validate gender input
        if (!validateGenderInput(genderInput)) {
            System.out.println("Error: invalid gender input");
            return;
        }
        genderInput = genderInput.toLowerCase();

        // validate age input
        if (!validateAgeInput(ageInput)) {
            System.out.println("Error: invalid age input");
            return;
        }

        // validate occupation input and convert to integer
        Integer occupationInputNo = encodeOccupation(occupationInput);
        if (occupationInputNo == -99) {
            System.out.println("Error: invalid occupation input");
            return;
        }

        // if there are genres input, validate
        genresInput = genresInput.stream().map(String::toLowerCase).collect(Collectors.toList());
        for (String g : genresInput) {
            if (!validateGenreInput(g)) {
                System.out.println("Error: invalid genre input");
                return;
            }
        }

        if (!validateLimitInput(limitInput)) {
            System.out.println("Error: invalid limit input");
            return;
        }

        int limitNo = 10;
        if (limitInput.length() != 0) {
            limitNo = Integer.parseInt(limitInput);
        }

        // prepare engine
        RecommendationEngine engine;
        engine = new RecommendationEngine();

        // run
        List<Movie> recommendations;
        recommendations = engine.recommendMovies(genderInput, ageInput, occupationInputNo, genresInput, titleInput, limitNo);

        // print result
        loadLinks("data/links.dat");
        for (Movie m: recommendations) {
            printMovie(m);
        }
    }
}
