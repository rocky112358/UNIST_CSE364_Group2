package se.group2;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


public class MovieblockControllerTest {
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
    public void loadLinksTest(){
        MovieblockController.loadLinks("data/links.dat");
    }

    @Rule
    public ExpectedException expectedExcetption = ExpectedException.none();

    @Test
    public void userRecommendationAgeTest() throws Exception{
        expectedExcetption.expect(InvalidInputException.class);
        expectedExcetption.expectMessage("Error: Age input is not given");

        MovieblockController testcontroller = new MovieblockController();
        UserRecommendationInput testinput = new UserRecommendationInput();
        testinput.setGender("F");
        //testinput.setAge("18");
        testinput.setOccupation("gradstudent");
        testinput.setGenres("Animation");
        testcontroller.userRecommendations(testinput);
    }

    @Test
    public void userRecommendationInvalidAgeTest() throws Exception{
        expectedExcetption.expect(InvalidInputException.class);
        expectedExcetption.expectMessage("Error: Invalid Age Input");

        MovieblockController testcontroller = new MovieblockController();
        UserRecommendationInput testinput = new UserRecommendationInput();
        testinput.setGender("F");
        testinput.setAge("-123");
        testinput.setOccupation("gradstudent");
        testinput.setGenres("Animation");
        testcontroller.userRecommendations(testinput);
    }

    @Test
    public void userRecommendationGenderTest() throws Exception{
        expectedExcetption.expect(InvalidInputException.class);
        expectedExcetption.expectMessage("Error: Gender input is not given");

        MovieblockController testcontroller = new MovieblockController();
        UserRecommendationInput testinput = new UserRecommendationInput();
        //testinput1.setGender("F");
        testinput.setAge("18");
        testinput.setOccupation("gradstudent");
        testinput.setGenres("Animation");
        testcontroller.userRecommendations(testinput);
    }

    @Test
    public void userRecommendationInvalidGenderTest() throws Exception{
        expectedExcetption.expect(InvalidInputException.class);
        expectedExcetption.expectMessage("Error: Invalid Gender Input");

        MovieblockController testcontroller = new MovieblockController();
        UserRecommendationInput testinput = new UserRecommendationInput();
        testinput.setGender("D");
        testinput.setAge("18");
        testinput.setOccupation("gradstudent");
        testinput.setGenres("Animation");
        testcontroller.userRecommendations(testinput);
    }

    @Test
    public void userRecommendationOccupationTest() throws Exception{
        expectedExcetption.expect(InvalidInputException.class);
        expectedExcetption.expectMessage("Error: Occupation input is not given");

        MovieblockController testcontroller = new MovieblockController();
        UserRecommendationInput testinput = new UserRecommendationInput();
        testinput.setGender("F");
        testinput.setAge("18");
        //testinput.setOccupation("gradstudent");
        testinput.setGenres("Animation");
        testcontroller.userRecommendations(testinput);
    }

    @Test
    public void userRecommendationInvalidOccupationTest() throws Exception{
        expectedExcetption.expect(InvalidInputException.class);
        expectedExcetption.expectMessage("Error: Invalid Occupation Input");

        MovieblockController testcontroller = new MovieblockController();
        UserRecommendationInput testinput = new UserRecommendationInput();
        testinput.setGender("F");
        testinput.setAge("18");
        testinput.setOccupation("nongdamgom");
        testinput.setGenres("Animation");
        testcontroller.userRecommendations(testinput);
    }

    @Test
    public void userRecommendationGenresTest() throws Exception{
        expectedExcetption.expect(InvalidInputException.class);
        expectedExcetption.expectMessage("Error: Genres input is not given");

        MovieblockController testcontroller = new MovieblockController();
        UserRecommendationInput testinput = new UserRecommendationInput();
        testinput.setGender("F");
        testinput.setAge("18");
        testinput.setOccupation("gradstudent");
        //testinput.setGenres("Animation");
        testcontroller.userRecommendations(testinput);
    }

    @Test
    public void userRecommendationInvalidGenresTest() throws Exception{
        expectedExcetption.expect(InvalidInputException.class);
        expectedExcetption.expectMessage("Error: invalid genres input");

        MovieblockController testcontroller = new MovieblockController();
        UserRecommendationInput testinput = new UserRecommendationInput();
        testinput.setGender("F");
        testinput.setAge("18");
        testinput.setOccupation("gradstudent");
        testinput.setGenres("Animation|nongdamgom");
        testcontroller.userRecommendations(testinput);
    }

     @Test
    public void movieRecommendationsTitleTest() throws Exception{
        expectedExcetption.expect(InvalidInputException.class);
        expectedExcetption.expectMessage("Error: Title input not given");

        MovieblockController testcontroller = new MovieblockController();
        MovieRecommendationInput testinput = new MovieRecommendationInput();

        //testinput.setTitle("");
        testcontroller.movieRecommendations(testinput);
    }

    @Test
    public void movieRecommendationsInvalidLimitTest() throws Exception{
        expectedExcetption.expect(InvalidInputException.class);
        expectedExcetption.expectMessage("Error: Invalid Limit Input (must be an positive integer)");

        MovieblockController testcontroller = new MovieblockController();
        MovieRecommendationInput testinput = new MovieRecommendationInput();

        testinput.setTitle("Toy Story (1995)");
        testinput.setLimit("-123");
        testcontroller.movieRecommendations(testinput);
    }
}