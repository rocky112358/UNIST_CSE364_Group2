package se.group2;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class MoviePoster {
    @Id
    int movieId;
    String imageLink;

    public MoviePoster(int movieId, String imageLink) {
        this.movieId = movieId;
        this.imageLink = imageLink;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

}
