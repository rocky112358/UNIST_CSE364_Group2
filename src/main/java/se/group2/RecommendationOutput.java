package se.group2;

public class RecommendationOutput {
    private final String title;
    private final String genres;
    private final String imdb;

    public RecommendationOutput(String t, String g, String i) {
        this.title = t;
        this.genres = g;
        this.imdb = i;
    }

    public String getTitle() {
        return this.title;
    }

    public String getGenres() {
        return this.genres;
    }

    public String getImdb() {
        return this.imdb;
    }
}
