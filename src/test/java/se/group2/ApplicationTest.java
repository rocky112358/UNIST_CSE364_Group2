package se.group2;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
public class ApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void usersRecommendationsTest() throws Exception {
        mockMvc.perform(
                get("/users/recommendations")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"age\": \"\", \"gender\": \"\", \"occupation\": \"\", \"genres\": \"\"}")
        ).andDo(print())
        .andExpect(
                status().isOk()
        )
        .andExpect(
                content().string(
                        containsString("[{\"title\":\"I Am Cuba (Soy Cuba/Ya Kuba) (1964)\",\"genres\":\"drama\",\"imdb\":\"http://www.imdb.com/title/tt0058604\"},{\"title\":\"Lamerica (1994)\",\"genres\":\"drama\",\"imdb\":\"http://www.imdb.com/title/tt0110299\"},{\"title\":\"Apple, The (Sib) (1998)\",\"genres\":\"drama\",\"imdb\":\"http://www.imdb.com/title/tt0156901\"},{\"title\":\"Sanjuro (1962)\",\"genres\":\"action|adventure\",\"imdb\":\"http://www.imdb.com/title/tt0056443\"},{\"title\":\"Seven Samurai (The Magnificent Seven) (Shichinin no samurai) (1954)\",\"genres\":\"action|drama\",\"imdb\":\"http://www.imdb.com/title/tt0047478\"},{\"title\":\"Shawshank Redemption, The (1994)\",\"genres\":\"drama\",\"imdb\":\"http://www.imdb.com/title/tt0111161\"},{\"title\":\"Godfather, The (1972)\",\"genres\":\"action|crime|drama\",\"imdb\":\"http://www.imdb.com/title/tt0068646\"},{\"title\":\"Close Shave, A (1995)\",\"genres\":\"animation|comedy|thriller\",\"imdb\":\"http://www.imdb.com/title/tt0112691\"},{\"title\":\"Usual Suspects, The (1995)\",\"genres\":\"crime|thriller\",\"imdb\":\"http://www.imdb.com/title/tt0114814\"},{\"title\":\"Schindler's List (1993)\",\"genres\":\"drama|war\",\"imdb\":\"http://www.imdb.com/title/tt0108052\"}]")
                )
        );
    }

    @Test
    public void moviesRecommendationsTest() throws Exception {
        mockMvc.perform(
                get("/movies/recommendations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Toy Story (1995)\", \"limit\": 5}")
        ).andDo(print())
                .andExpect(
                        status().isOk()
                )
                .andExpect(
                        content().string(
                                containsString("[{\"title\":\"Close Shave, A (1995)\",\"genres\":\"animation|comedy|thriller\",\"imdb\":\"http://www.imdb.com/title/tt0112691\"},{\"title\":\"Wrong Trousers, The (1993)\",\"genres\":\"animation|comedy\",\"imdb\":\"http://www.imdb.com/title/tt0108598\"},{\"title\":\"Wallace & Gromit: The Best of Aardman Animation (1996)\",\"genres\":\"animation\",\"imdb\":\"http://www.imdb.com/title/tt0118114\"},{\"title\":\"Yojimbo (1961)\",\"genres\":\"comedy|drama|western\",\"imdb\":\"http://www.imdb.com/title/tt0055630\"},{\"title\":\"City Lights (1931)\",\"genres\":\"comedy|drama|romance\",\"imdb\":\"http://www.imdb.com/title/tt0021749\"}]")
                        )
                );
    }

}
