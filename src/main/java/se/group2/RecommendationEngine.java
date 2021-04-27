package se.group2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecommendationEngine {
    private static List<Movie> movies;
    private static List<Rating> ratings;
    private static List<User> users;

    public RecommendationEngine(){
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
                User user = new User(properties[0], properties[1], properties[2], properties[3], properties[4]);
                u.add(user);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
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
        }
        ratings = r;
    }

    public List<Movie> recommendMovies(String genderInput, String ageInput, String occupationInput) {
        List<Movie> recommendations = new ArrayList<>();

        if (movies.size() == 0) {
            System.out.println("[ERROR] no movie data loaded.");
            System.exit(-1);
        }
        if (ratings.size() == 0) {
            System.out.println("[ERROR] no rating data loaded.");
            System.exit(-1);
        }
        if (users.size() == 0) {
            System.out.println("[ERROR] no user data loaded.");
            System.exit(-1);
        }

        // TODO: implement recommendation
        for (Movie m: movies) {
            recommendations.add(m);
            if (recommendations.size() >= 10) {
                break;
            }
        }
        return recommendations;
    }
}
