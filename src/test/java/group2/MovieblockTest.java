package se.group2;
import org.junit.Test;
import org.junit.Before;
import java.util.Map;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class MovieblockTest{
    public static Map<String, Integer> occupationMap = new HashMap<>();

    @Before
    public void setUp(){
        occupationMap.put("", -1);
        occupationMap.put("other", 0);
        occupationMap.put("academic", 1);
        occupationMap.put("educator", 1);
        occupationMap.put("artist", 2);
        occupationMap.put("clerical", 3);
        occupationMap.put("admin", 3);
        occupationMap.put("collegestudent", 4);
        occupationMap.put("college student", 4);
        occupationMap.put("gradstudent", 4);
        occupationMap.put("grad student", 4);
        occupationMap.put("customerservice", 5);
        occupationMap.put("customer service", 5);
        occupationMap.put("doctor", 6);
        occupationMap.put("healthcare", 6);
        occupationMap.put("health care", 6);
        occupationMap.put("executive", 7);
        occupationMap.put("managerial", 7);
        occupationMap.put("farmer", 8);
        occupationMap.put("homemaker", 9);
        occupationMap.put("k-12student", 10);
        occupationMap.put("k-12 student", 10);
        occupationMap.put("lawyer", 11);
        occupationMap.put("programmer", 12);
        occupationMap.put("retired", 13);
        occupationMap.put("sales", 14);
        occupationMap.put("marketing", 14);
        occupationMap.put("scientist", 15);
        occupationMap.put("self-employed", 16);
        occupationMap.put("technician", 17);
        occupationMap.put("engineer", 17);
        occupationMap.put("tradesman", 18);
        occupationMap.put("craftsman", 18);
        occupationMap.put("unemployed", 19);
        occupationMap.put("writer", 20);
    }

    @Test
    public void validateGenderInputTest(){
        String test_female = "F";
        String test_male = "M";
        String test_everyone = "";
        String test_wrong = "D";
        assertEquals(true, Movieblock.validateGenderInput(test_female));
        assertEquals(true, Movieblock.validateGenderInput(test_male));
        assertEquals(true, Movieblock.validateGenderInput(test_everyone));
        assertEquals(false, Movieblock.validateGenderInput(test_wrong));
    }

    @Test
    public void validateAgeInputTest(){
        String test_everyone = "";
        String test_normal = "100";
        String test_zero = "0";
        String test_negative = "-1";
        assertEquals(true, Movieblock.validateAgeInput(test_everyone));
        assertEquals(true, Movieblock.validateAgeInput(test_normal));
        assertEquals(true, Movieblock.validateAgeInput(test_zero));
        assertEquals(false, Movieblock.validateAgeInput(test_negative));
    }

    @Test
    public void validateOccupationInputTest(){
        String test_other = "other";
        String test_gradstudent = "grad student";
        String test_everyone = "";
        String test_wrong = "Dog";

        assertEquals(true, Movieblock.validateOccupationInput(test_other, MovieblockTest.occupationMap));
        assertEquals(true, Movieblock.validateOccupationInput(test_gradstudent, MovieblockTest.occupationMap));
        assertEquals(true, Movieblock.validateOccupationInput(test_everyone, MovieblockTest.occupationMap));
        assertEquals(false, Movieblock.validateOccupationInput(test_wrong, MovieblockTest.occupationMap));
    }

    @Test
    public void validateGenreInputTest(){
        String test_adventure = "adventure";
        String test_uppercase = "AdVenture";
        String test_everything = "";
        String test_wrong = "Dog";
        assertEquals(true, Movieblock.validateGenreInput(test_adventure));
        assertEquals(false, Movieblock.validateGenreInput(test_uppercase));
        assertEquals(true, Movieblock.validateGenreInput(test_everything));
        assertEquals(false, Movieblock.validateGenreInput(test_wrong));
    }

    @Test
    public void encodeOccupationTest(){
        String test_academic = "academic";
        String test_uppercase = "AcadEmic";
        String test_everything = "";
        assertSame(1, Movieblock.encodeOccupation(test_academic, MovieblockTest.occupationMap));
        assertSame(1, Movieblock.encodeOccupation(test_uppercase, MovieblockTest.occupationMap));
        assertSame(-1, Movieblock.encodeOccupation(test_everything, MovieblockTest.occupationMap));
    }
}
