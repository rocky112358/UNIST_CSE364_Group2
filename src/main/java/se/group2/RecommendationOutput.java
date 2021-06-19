package se.group2;

public class RecommendationOutput {
    private final int id;
    private final String title;
    private final String genres;
    private final String imdb;

    public RecommendationOutput(int id, String t, String g, String i) {
        this.id = id;
        this.title = t;
        this.genres = g;
        this.imdb = i;
    }

    public int getId() {
        return this.id;
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
