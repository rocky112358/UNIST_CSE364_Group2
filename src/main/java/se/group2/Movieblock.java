package se.group2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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


public class Movieblock {
    public static void main(String args[]) {
        List<Movie> movies = new ArrayList<Movie>();
        List<User> users = new ArrayList<User>();
        List<Rating> ratings = new ArrayList<Rating>();

        try {
            BufferedReader br = new BufferedReader(new FileReader("data/movies.dat"));
            String line;
            while((line = br.readLine()) != null) {
                String[] properties = line.split("::");
                Movie movie = new Movie(properties[0], properties[1], properties[2]);
                movies.add(movie);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader("data/users.dat"));
            String line;
            while((line = br.readLine()) != null) {
                String[] properties = line.split("::");
                User user = new User(properties[0], properties[1], properties[2], properties[3], properties[4]);
                users.add(user);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader("data/ratings.dat"));
            String line;
            while((line = br.readLine()) != null) {
                String[] properties = line.split("::");
                Rating rating = new Rating(properties[0], properties[1], properties[2], properties[3]);
                ratings.add(rating);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
