package org.whitehat.airports;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class WebControllerTest {
    private static final ObjectMapper om = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @Before
    public void init() {
    }

    // GET all
    @Test
    public void itShouldFetchAllAirports() throws Exception {
        mockMvc.perform(get("/airports/"))
                .andExpect(status().isOk())
                .andExpect(content().string("[\"LHR\",\"NRT\",\"LAX\"]"));
    }

    // GET one
    @Test
    public void itShouldReturnOneAirport() throws Exception {
        String airport = "LHR";
        mockMvc.perform(get(String.format("/airports/airport?%s", airport)))
                .andExpect(status().isOk())
                .andExpect(content().string(airport));
    }

    // GET one 404
    @Test
    public void itShouldFetchReturnNotFound() throws Exception {
        String airport = "TPA";
        mockMvc.perform(get(String.format("/airports/airport", airport)))
                .andExpect(status().is(404));
    }

    // POST one
    @Test
    public void itShouldAddAirportToList() throws Exception {
        String newAirport = "HND";
        mockMvc.perform(post(String.format("/airports/new?%s", newAirport)))
                .andExpect(status().is(201))
                .andExpect(content().string(String.format("Succesfully added airport %s", newAirport)));
    }

    public void itShouldDeleteAllAirport() throws Exception {
        mockMvc.perform(get("/airports/"))
                .andExpect(status().isOk())
                .andExpect(content().string("[\"LHR\",\"NRT\",\"LAX\"]"));
    }

    // DELETE all

//    private static void printJSON(Object object) {
//        String result;
//        try {
//            result = om.writerWithDefaultPrettyPrinter().writeValueAsString(object);
//            System.out.println(result);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//    }
}