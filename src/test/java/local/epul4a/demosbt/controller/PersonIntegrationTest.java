package local.epul4a.demosbt.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import local.epul4a.demosbt.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class PersonIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PersonRepository repository;

    @Test
    void addedPersonIsSavedInDatabaseAndListed() throws Exception {
        mvc.perform(post("/addPerson")
                        .param("firstName", "Alan")
                        .param("lastName", "Turing")
                        .param("email", "alan@turing.org"))
                .andExpect(redirectedUrl("/personList"));

        assertThat(repository.findAll())
                .anyMatch(p -> "Turing".equals(p.getLastName()));

        mvc.perform(get("/personList"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Turing")));
    }
}