package local.epul4a.demosbt.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(MainController.class)
class MainControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void personListShowsInitialPersons() throws Exception {
        mvc.perform(get("/personList"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Gates")));
    }

    @Test
    void invalidFormStaysOnFormWithErrors() throws Exception {
        mvc.perform(post("/addPerson")
                        .param("firstName", "")
                        .param("lastName", "Turing"))
                .andExpect(status().isOk())
                .andExpect(view().name("addPerson"))
                .andExpect(model().attributeHasFieldErrors("personForm", "firstName"));
    }

    @Test
    void validFormRedirectsToList() throws Exception {
        mvc.perform(post("/addPerson")
                        .param("firstName", "Alan")
                        .param("lastName", "Turing"))
                .andExpect(redirectedUrl("/personList"));
    }
}