package se.group2;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {
    @Id
    private int id;
    private String gender;
    private int age;
    private int occupation;
    private String zipcode;

    public User(int id, String gender, int age, int occupation, String zipcode) {
        this.id = id;
        this.gender = gender;
        this.age = age;
        this.occupation = occupation;
        this.zipcode = zipcode;
    }

    public int getId() {
        return id;
    }

    public void setId(String id) {
        this.id = Integer.parseInt(id);
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getOccupation() {
        return occupation;
    }

    public void setOccupation(int occupation) {
        this.occupation = occupation;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public boolean canBeAge(String a) {
        if (a.equals("")) {
            return false;
        } else if (age == 1) {
            return Integer.parseInt(a) < 18;
        } else if (age == 18) {
            return 18 <= Integer.parseInt(a) && Integer.parseInt(a) <= 24;
        } else if (age == 25) {
            return 25 <= Integer.parseInt(a) && Integer.parseInt(a) <= 34;
        } else if (age == 35) {
            return 35 <= Integer.parseInt(a) && Integer.parseInt(a) <= 44;
        } else if (age == 45) {
            return 45 <= Integer.parseInt(a) && Integer.parseInt(a) <= 49;
        } else if (age == 50) {
            return 50 <= Integer.parseInt(a) && Integer.parseInt(a) <= 55;
        } else if (age == 56) {
            return 56 <= Integer.parseInt(a);
        } else {
            return false;
        }
    }
}
