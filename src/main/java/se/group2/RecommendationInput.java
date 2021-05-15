package se.group2;

public class RecommendationInput {
    private String gender;
    private String age;
    private String occupation;
    private String genres;
    private String title;
    private Integer limit;

    public RecommendationInput() {
        gender = "";
        age = "";
        occupation = "";
        genres = "";
        title = "";
        limit = 10;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String g) {
        gender = g;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String a) {
        age = a;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String o) {
        occupation = o;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String g) {
        genres = g;
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
