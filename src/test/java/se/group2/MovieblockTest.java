package se.group2;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


public class MovieblockTest {

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

    @Test
    public void validateGenderInputTest() {
        String test_female = "F";
        String test_female_lowercase = "f";
        String test_male = "M";
        String test_male_lowercase = "m";
        String test_everyone = "";
        String test_wrong = "D";
        assertTrue(Movieblock.validateGenderInput(test_female));
        assertTrue(Movieblock.validateGenderInput(test_female_lowercase));
        assertTrue(Movieblock.validateGenderInput(test_male));
        assertTrue(Movieblock.validateGenderInput(test_male_lowercase));
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

        assertNotSame(-99, Movieblock.encodeOccupation(test_other));
        assertNotSame(-99,Movieblock.encodeOccupation(test_gradstudent));
        assertNotSame(-99,Movieblock.encodeOccupation(test_everyone));
        assertSame(-99,Movieblock.encodeOccupation(test_wrong));
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
    public void validateLimitInputTest() {
        String test_empty = "";
        String test_invalid = "-1.23#4";
        String test_valid = "1234";
        String test_negative = "-1234";
        assertTrue(Movieblock.validateLimitInput(test_empty));
        assertFalse(Movieblock.validateLimitInput(test_invalid));
        assertTrue(Movieblock.validateLimitInput(test_valid));
        assertFalse(Movieblock.validateLimitInput(test_negative));
    }

    @Test
    public void encodeOccupationTest() {
        String test_empty = "";
        String test_uppercase = "AcadEmic";
        String test_other = "other";
        String test_academic = "academic";
        String test_educator = "educator";
        String test_artist = "artist";
        String test_clerical = "clerical";
        String test_admin = "admin";
        String test_collegestudent = "collegestudent";
        String test_college_student = "college student";
        String test_gradstudent = "gradstudent";
        String test_grad_student = "grad student";
        String test_customerservice = "customerservice";
        String test_customer_service = "customer service";
        String test_doctor = "doctor";
        String test_healthcare = "healthcare";
        String test_health_care = "health care";
        String test_executive = "executive";
        String test_managerial = "managerial";
        String test_farmer = "farmer";
        String test_homemaker = "homemaker";
        String test_k_12_student = "k-12student";
        String test_k_12__student = "k-12 student";
        String test_lawyer = "lawyer";
        String test_programmer = "programmer";
        String test_retired = "retired";
        String test_sales = "sales";
        String test_marketing = "marketing";
        String test_scientist = "scientist";
        String test_self_employed = "self-employed";
        String test_technician = "technician";
        String test_engineer = "engineer";
        String test_tradesman = "tradesman";
        String test_craftsman = "craftsman";
        String test_unemployed = "unemployed";
        String test_writer = "writer";
        String test_invalid = "wizard";
        assertSame(-1, Movieblock.encodeOccupation(test_empty));
        assertSame(1, Movieblock.encodeOccupation(test_uppercase));
        assertSame(0, Movieblock.encodeOccupation(test_other));
        assertSame(1, Movieblock.encodeOccupation(test_academic));
        assertSame(1, Movieblock.encodeOccupation(test_educator));
        assertSame(2, Movieblock.encodeOccupation(test_artist));
        assertSame(3, Movieblock.encodeOccupation(test_clerical));
        assertSame(3, Movieblock.encodeOccupation(test_admin));
        assertSame(4, Movieblock.encodeOccupation(test_collegestudent));
        assertSame(4, Movieblock.encodeOccupation(test_college_student));
        assertSame(4, Movieblock.encodeOccupation(test_gradstudent));
        assertSame(4, Movieblock.encodeOccupation(test_grad_student));
        assertSame(5, Movieblock.encodeOccupation(test_customerservice));
        assertSame(5, Movieblock.encodeOccupation(test_customer_service));
        assertSame(6, Movieblock.encodeOccupation(test_doctor));
        assertSame(6, Movieblock.encodeOccupation(test_healthcare));
        assertSame(6, Movieblock.encodeOccupation(test_health_care));
        assertSame(7, Movieblock.encodeOccupation(test_executive));
        assertSame(7, Movieblock.encodeOccupation(test_managerial));
        assertSame(8, Movieblock.encodeOccupation(test_farmer));
        assertSame(9, Movieblock.encodeOccupation(test_homemaker));
        assertSame(10, Movieblock.encodeOccupation(test_k_12_student));
        assertSame(10, Movieblock.encodeOccupation(test_k_12__student));
        assertSame(11, Movieblock.encodeOccupation(test_lawyer));
        assertSame(12, Movieblock.encodeOccupation(test_programmer));
        assertSame(13, Movieblock.encodeOccupation(test_retired));
        assertSame(14, Movieblock.encodeOccupation(test_sales));
        assertSame(14, Movieblock.encodeOccupation(test_marketing));
        assertSame(15, Movieblock.encodeOccupation(test_scientist));
        assertSame(16, Movieblock.encodeOccupation(test_self_employed));
        assertSame(17, Movieblock.encodeOccupation(test_technician));
        assertSame(17, Movieblock.encodeOccupation(test_engineer));
        assertSame(18, Movieblock.encodeOccupation(test_tradesman));
        assertSame(18, Movieblock.encodeOccupation(test_craftsman));
        assertSame(19, Movieblock.encodeOccupation(test_unemployed));
        assertSame(20, Movieblock.encodeOccupation(test_writer));
        assertSame(-99, Movieblock.encodeOccupation(test_invalid));
    }

    @Test
    public void userCanBeAgeTest() {
        User u = new User(1, "f", 1, 1, "");
        assertFalse(u.canBeAge(""));
        assertTrue(u.canBeAge("17"));
        assertFalse(u.canBeAge("18"));
        u = new User(1, "f", 18, 1, "");
        assertFalse(u.canBeAge("17"));
        assertTrue(u.canBeAge("18"));
        assertTrue(u.canBeAge("24"));
        assertFalse(u.canBeAge("25"));
        u = new User(1, "f", 25, 1, "");
        assertFalse(u.canBeAge("24"));
        assertTrue(u.canBeAge("25"));
        assertTrue(u.canBeAge("34"));
        assertFalse(u.canBeAge("35"));
        u = new User(1, "f", 35, 1, "");
        assertTrue(u.canBeAge("35"));
        assertFalse(u.canBeAge("45"));
        u = new User(1, "f", 45, 1, "");
        assertTrue(u.canBeAge("45"));
        assertFalse(u.canBeAge("50"));
        u = new User(1, "f", 50, 1, "");
        assertTrue(u.canBeAge("50"));
        assertFalse(u.canBeAge("56"));
        u = new User(1, "f", 56, 1, "");
        assertTrue(u.canBeAge("56"));
        assertFalse(u.canBeAge("1"));
        u = new User(1, "f", 999, 1, "");
        assertFalse(u.canBeAge("1"));
    }

    @Test
    public void lessArgumentTest() {
        String[] args = {"", ""};

        Movieblock.main(args);

        assertEquals("Args: gender age occupation genre(s) title limit", outStream.toString().strip());
    }

    @Test
    public void moreArgumentTest() {
        String[] args = {"F", "25", "gradstudent", "drama", "", "10", "more_arg"};

        Movieblock.main(args);

        assertEquals("Args: gender age occupation genre(s) title limit", outStream.toString().strip());
    }

    @Test
    public void invalidGenderTest() {
        String[] invalid_gender = {"D", "", "", "", "", ""};

        Movieblock.main(invalid_gender);

        assertEquals("Error: invalid gender input", outStream.toString().strip());
    }

    @Test
    public void invalidAgeTest() {
        String[] invalid_age = {"", "-15", "", "", "", ""};

        Movieblock.main(invalid_age);

        assertEquals("Error: invalid age input", outStream.toString().strip());
    }

    @Test
    public void invalidOccupationTest() {
        String[] invalid_occupation = {"", "", "dog", "", "", ""};

        Movieblock.main(invalid_occupation);

        assertEquals("Error: invalid occupation input", outStream.toString().strip());
    }

    @Test
    public void invalidGenreTest() {
        String[] invalid_genre = {"", "", "", "dog", "", ""};

        Movieblock.main(invalid_genre);

        assertEquals("Error: invalid genre input", outStream.toString().strip());
    }

    @Test
    public void invalidLimitTest() {
        String[] invalid_link = {"", "", "", "", "", "12#3"};

        Movieblock.main(invalid_link);

        assertEquals("Error: invalid limit input", outStream.toString().strip());
    }
}
