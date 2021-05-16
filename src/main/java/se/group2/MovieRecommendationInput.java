package se.group2;

public class MovieRecommendationInput {

    private String title;
    private Integer limit;

    public MovieRecommendationInput() {
        title = "";
        limit = 10;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String t) {
        title = t;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer l) {
        limit = l;
    }
}

