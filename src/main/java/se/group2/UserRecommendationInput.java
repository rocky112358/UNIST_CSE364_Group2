package se.group2;

public class UserRecommendationInput {
    private String gender;
    private String age;
    private String occupation;
    private String genres;

    public UserRecommendationInput() {
        gender = null;
        age = null;
        occupation = null;
        genres = null;
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
}