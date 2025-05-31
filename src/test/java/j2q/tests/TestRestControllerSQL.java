package j2q.tests;

import j2q.setup.controller.RestControllerSQL;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.hamcrest.Matchers.*;

@WebMvcTest(RestControllerSQL.class)
public class TestRestControllerSQL {
    private @Autowired MockMvc mockMvc;

    @Test
    public void testGetAutoNumberingSQL() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/AutoNumberingSQL?type=ALL"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(is(equalToCompressingWhiteSpace("SELECT Sys_RecID, Sys_EntityType, Sys_EntityNumber   FROM Sys_AutoNumbering"))
                ));
    }
}
