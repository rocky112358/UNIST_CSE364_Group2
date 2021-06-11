package se.group2;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Link {
    @Id
    int movieId;
    String imdbId;

    public Link(int movieId, String imdbId) {
        this.movieId = movieId;
        this.imdbId = imdbId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

}
