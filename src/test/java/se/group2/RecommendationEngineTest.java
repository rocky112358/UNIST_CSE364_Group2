package se.group2;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class RecommendationEngineTest {
    @Test
    public void loadUsersTest() {
        RecommendationEngine r = new RecommendationEngine();
        r.loadUsers("data/users.dat");
        User u = r.getUserById(1);
        assertEquals(1, u.id);
        assertEquals("f", u.gender);
        assertEquals(1, u.age);
        assertEquals(10, u.occupation);
        assertEquals("48067", u.zipcode);
    }

    @Test
    public void loadRatingsTest() {
        RecommendationEngine r = new RecommendationEngine();
        r.loadRatings("data/ratings.dat");
        Rating ra = r.getRatingByUserAndMovie(1, 1193);
        assertEquals(1, ra.userId);
        assertEquals(1193, ra.movieId);
        assertEquals(5, ra.rating);
        assertEquals(978300760, ra.timestamp);
    }

    @Test
    public void loadMoviesTest() {
        RecommendationEngine r = new RecommendationEngine();
        r.loadMovies("data/movies.dat");
        Movie m = r.getMovieById(1);
        assertEquals(1, m.id);
        assertEquals("Toy Story (1995)", m.title);
        assertEquals(Arrays.asList("animation", "children's", "comedy"), m.genre);
    }

    @Test
    public void getRatingByUserAndMovieTest() {
        RecommendationEngine r = new RecommendationEngine();
        r.loadRatings("data/ratings.dat");
        Rating ra = r.getRatingByUserAndMovie(1, 1193);
        assertEquals(1, ra.userId);
        assertEquals(1193, ra.movieId);
        assertEquals(5, ra.rating);
        assertEquals(978300760, ra.timestamp);
        ra = r.getRatingByUserAndMovie(99, 99999);
        assertNull(ra);
    }

    @Test
    public void getUserByIdTest() {
        RecommendationEngine r = new RecommendationEngine();
        r.loadUsers("data/users.dat");
        User u = r.getUserById(1);
        assertEquals(1, u.id);
        assertEquals("f", u.gender);
        assertEquals(1, u.age);
        assertEquals(10, u.occupation);
        assertEquals("48067", u.zipcode);
        u = r.getUserById(99999);
        assertNull(u);
    }

    @Test
    public void getMovieByIdTest() {
        RecommendationEngine r = new RecommendationEngine();
        r.loadMovies("data/movies.dat");
        Movie m = r.getMovieById(1);
        assertEquals(1, m.id);
        assertEquals("Toy Story (1995)", m.title);
        assertEquals(Arrays.asList("animation", "children's", "comedy"), m.genre);
        m = r.getMovieById(99999);
        assertNull(m);
    }

    @Test
    public void automaticLoadDataTest() {
        RecommendationEngine r = new RecommendationEngine();
        List<Movie> recommend = r.recommendMovies("", "", -1, Collections.emptyList());
        assertEquals(10, recommend.size());

        User u = r.getUserById(1);
        assertEquals(1, u.id);
        assertEquals("f", u.gender);
        assertEquals(1, u.age);
        assertEquals(10, u.occupation);
        assertEquals("48067", u.zipcode);

        Rating ra = r.getRatingByUserAndMovie(1, 1193);
        assertEquals(1, ra.userId);
        assertEquals(1193, ra.movieId);
        assertEquals(5, ra.rating);
        assertEquals(978300760, ra.timestamp);

        Movie m = r.getMovieById(1);
        assertEquals(1, m.id);
        assertEquals("Toy Story (1995)", m.title);
        assertEquals(Arrays.asList("animation", "children's", "comedy"), m.genre);
    }

    @Test
    public void manualLoadDataTest() {
        RecommendationEngine r = new RecommendationEngine();
        r.loadRatings("data/ratings.dat");
        r.loadUsers("data/users.dat");
        r.loadMovies("data/movies.dat");
        List<Movie> recommend = r.recommendMovies("", "", -1, Collections.emptyList());
        assertEquals(10, recommend.size());
    }

    @Test
    public void groupDivisionTest() {
        RecommendationEngine r = new RecommendationEngine();
        List<Movie> recommend = r.recommendMovies("m", "", -1, Collections.emptyList());
        assertEquals(10, recommend.size());
        recommend = r.recommendMovies("", "1", -1, Collections.emptyList());
        assertEquals(10, recommend.size());
        recommend = r.recommendMovies("", "", 1, Collections.emptyList());
        assertEquals(10, recommend.size());
    }

    @Test
    public void genreFilterTest() {
        RecommendationEngine r = new RecommendationEngine();
        List<Movie> recommend = r.recommendMovies("m", "", -1, Collections.singletonList("drama"));
        for (Movie m : recommend) {
            assertTrue(m.genre.contains("drama"));
        }
    }
}
