import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import com.itops.events.EventsApplication;

@SpringBootTest(classes = EventsApplication.class)
@AutoConfigureMockMvc
public class EventResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        // Setup logic (if needed)
    }

    @Test
    public void testCreateEvent() throws Exception {
        String eventJson = "{ \"name\": \"Test Event\", \"location\": \"Test Location\" }";
        mockMvc.perform(post("/api/events")
                .contentType("application/json")
                .content(eventJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetEventById() throws Exception {
        mockMvc.perform(get("/api/events/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testGetAllEvents() throws Exception {
        mockMvc.perform(get("/api/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    public void testUpdateEvent() throws Exception {
        String updatedEventJson = "{ \"name\": \"Updated Event\", \"location\": \"Updated Location\" }";
        mockMvc.perform(put("/api/events/1")
                .contentType("application/json")
                .content(updatedEventJson))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteEvent() throws Exception {
        mockMvc.perform(delete("/api/events/1"))
                .andExpect(status().isNoContent());
    }
}