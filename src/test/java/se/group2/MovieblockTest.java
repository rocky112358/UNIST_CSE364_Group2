package se.group2;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.*;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


public class MovieblockTest {
    public static Map<String, Integer> occupationMap = new HashMap<>();

    private final ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outStream));
        System.setErr(new PrintStream(errStream));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Before
    public void setUp() {
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
    public void validateGenderInputTest() {
        String test_female = "F";
        String test_male = "M";
        String test_everyone = "";
        String test_wrong = "D";
        assertTrue(Movieblock.validateGenderInput(test_female));
        assertTrue(Movieblock.validateGenderInput(test_male));
        assertTrue(Movieblock.validateGenderInput(test_everyone));
        assertFalse(Movieblock.validateGenderInput(test_wrong));
    }

    @Test
    public void validateAgeInputTest() {
        String test_everyone = "";
        String test_normal = "100";
        String test_zero = "0";
        String test_negative = "-1";
        assertTrue(Movieblock.validateAgeInput(test_everyone));
        assertTrue(Movieblock.validateAgeInput(test_normal));
        assertTrue(Movieblock.validateAgeInput(test_zero));
        assertFalse(Movieblock.validateAgeInput(test_negative));
    }

    @Test
    public void validateOccupationInputTest() {
        String test_other = "other";
        String test_gradstudent = "grad student";
        String test_everyone = "";
        String test_wrong = "Dog";

        assertTrue(Movieblock.validateOccupationInput(test_other, MovieblockTest.occupationMap));
        assertTrue(Movieblock.validateOccupationInput(test_gradstudent, MovieblockTest.occupationMap));
        assertTrue(Movieblock.validateOccupationInput(test_everyone, MovieblockTest.occupationMap));
        assertFalse(Movieblock.validateOccupationInput(test_wrong, MovieblockTest.occupationMap));
    }

    @Test
    public void validateGenreInputTest() {
        String test_adventure = "adventure";
        String test_uppercase = "AdVenture";
        String test_everything = "";
        String test_wrong = "Dog";
        assertTrue(Movieblock.validateGenreInput(test_adventure));
        assertFalse(Movieblock.validateGenreInput(test_uppercase));
        assertTrue(Movieblock.validateGenreInput(test_everything));
        assertFalse(Movieblock.validateGenreInput(test_wrong));
    }

    @Test
    public void encodeOccupationTest() {
        String test_academic = "academic";
        String test_uppercase = "AcadEmic";
        String test_everything = "";
        assertSame(1, Movieblock.encodeOccupation(test_academic, MovieblockTest.occupationMap));
        assertSame(1, Movieblock.encodeOccupation(test_uppercase, MovieblockTest.occupationMap));
        assertSame(-1, Movieblock.encodeOccupation(test_everything, MovieblockTest.occupationMap));
    }

    @Test
    public void userCanBeAgeTest() {
        User u = new User("1", "F", "1", "1", "");
        assertFalse(u.canBeAge(""));
        assertTrue(u.canBeAge("17"));
        assertFalse(u.canBeAge("18"));
        u = new User("1", "F", "18", "1", "");
        assertFalse(u.canBeAge("17"));
        assertTrue(u.canBeAge("18"));
        assertTrue(u.canBeAge("24"));
        assertFalse(u.canBeAge("25"));
        u = new User("1", "F", "25", "1", "");
        assertFalse(u.canBeAge("24"));
        assertTrue(u.canBeAge("25"));
        assertTrue(u.canBeAge("34"));
        assertFalse(u.canBeAge("35"));
        u = new User("1", "F", "35", "1", "");
        assertTrue(u.canBeAge("35"));
        assertFalse(u.canBeAge("45"));
        u = new User("1", "F", "45", "1", "");
        assertTrue(u.canBeAge("45"));
        assertFalse(u.canBeAge("50"));
        u = new User("1", "F", "50", "1", "");
        assertTrue(u.canBeAge("50"));
        assertFalse(u.canBeAge("56"));
        u = new User("1", "F", "56", "1", "");
        assertTrue(u.canBeAge("56"));
        assertFalse(u.canBeAge("1"));
        u = new User("1", "F", "999", "1", "");
        assertFalse(u.canBeAge("1"));
    }

    @Test
    public void lessArgumentTest() {
        String[] args = {"", ""};

        Movieblock.main(args);

        assertEquals("Args: gender age occupation [genre(s)]", outStream.toString().strip());
    }

    @Test
    public void normalArgumentTest1() {
        String[] args = {"F", "25", "gradstudent"};

        Movieblock.main(args);
    }

    @Test
    public void normalArgumentTest2() {
        String[] args = {"F", "25", "gradstudent", "drama"};

        Movieblock.main(args);
    }

    @Test
    public void moreArgumentTest() {
        String[] args = {"F", "25", "gradstudent", "drama", "more_arg"};

        Movieblock.main(args);

        assertEquals("Args: gender age occupation [genre(s)]", outStream.toString().strip());
    }

    @Test
    public void blankGenreTest() {
        String[] test_four = {"", "", "", ""};

        Movieblock.main(test_four);

        assertEquals("Error: invalid genre input", outStream.toString().strip());
    }

    @Test
    public void invalidGenderTest() {
        String[] invalid_gender = {"D", "", ""};

        Movieblock.main(invalid_gender);

        assertEquals("Error: invalid gender input", outStream.toString().strip());
    }

    @Test
    public void invalidAgeTest() {
        String[] invalid_age = {"", "-15", ""};

        Movieblock.main(invalid_age);

        assertEquals("Error: invalid age input", outStream.toString().strip());
    }

    @Test
    public void invalidOccupationTest() {
        String[] invalid_occupation = {"", "", "dog"};

        Movieblock.main(invalid_occupation);

        assertEquals("Error: invalid occupation input", outStream.toString().strip());
    }

    @Test
    public void invalidGenreTest() {
        String[] invalid_genre = {"", "", "", "dog"};

        Movieblock.main(invalid_genre);

        assertEquals("Error: invalid genre input", outStream.toString().strip());
    }
}
