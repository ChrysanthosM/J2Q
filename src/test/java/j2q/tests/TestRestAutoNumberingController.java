package j2q.tests;

import j2q.setup.controller.RestAutoNumberingController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(RestAutoNumberingController.class)
public class TestRestAutoNumberingController {
    private @Autowired MockMvc mockMvc;

    @Test
    public void testGetAutoNumberingList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/AutoNumberingList?type=ALL"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    public void testGetAutoNumberingListAsync() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/AutoNumberingListAsync?type=ALL"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

}
