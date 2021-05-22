package se.group2;

public class MovieRecommendationInput {

    private String title;
    private String limit;

    public MovieRecommendationInput() {
        title = null;
        limit = "10";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String t) {
        title = t;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String l) {
        limit = l;
    }
}

