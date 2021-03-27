package se.group2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
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
        if (args.length != 2) {
            System.out.println("Error: 2 args are required");
            System.exit(-1);
        }
        String[] genres = args[0].split("\\|");
        String occupation = args[1];

        String[] genreArray = {"action", "adventure", "animation", "children's", "comedy", "crime", "documentary", "drama", "fantasy", "film-noir", "horror", "musical", "mystery", "romance", "sci-fi", "thriller", "war", "western"};
        Map<String, Integer> occupationMap = new HashMap<String, Integer>();
        occupationMap.put("other", 0);
        occupationMap.put("academic", 1);
        occupationMap.put("educator", 1);
        occupationMap.put("artist", 2);
        occupationMap.put("clerical", 3);
        occupationMap.put("admin", 3);
        occupationMap.put("collegestudent", 4);
        occupationMap.put("gradstudent", 4);
        occupationMap.put("customerservice", 5);
        occupationMap.put("doctor", 6);
        occupationMap.put("healthcare", 6);
        occupationMap.put("executive", 7);
        occupationMap.put("managerial", 7);
        occupationMap.put("farmer", 8);
        occupationMap.put("homemaker", 9);
        occupationMap.put("k-12student", 0);
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

        // validate input with genre list and occupation map
        for (String g: genres) {
            if (!Arrays.asList(genreArray).contains(g)) {
                System.out.println("Error: invalid genre input");
                System.exit(-1);
            }
        }

        Integer occupationNo = occupationMap.get(occupation.toLowerCase());
        if (occupationNo == null) {
            System.out.println("Error: invalid occupation input");
            System.exit(-1);
        }



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
