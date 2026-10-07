package local.epul4a.demosbt.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import local.epul4a.demosbt.model.Person;
import local.epul4a.demosbt.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MainController.class)
class MainControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private PersonRepository repository;

    @Test
    void personListShowsPersons() throws Exception {
        when(repository.findAll())
                .thenReturn(List.of(new Person("Bill", "Gates", "bill@microsoft.com")));

        mvc.perform(get("/personList"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Gates")));
    }

    @Test
    void invalidFormStaysOnFormWithErrors() throws Exception {
        mvc.perform(post("/addPerson")
                        .param("firstName", "")
                        .param("lastName", "Turing")
                        .param("email", "alan@turing.org"))
                .andExpect(status().isOk())
                .andExpect(view().name("addPerson"))
                .andExpect(model().attributeHasFieldErrors("personForm", "firstName"));
    }

    @Test
    void invalidEmailIsRejected() throws Exception {
        mvc.perform(post("/addPerson")
                        .param("firstName", "Alan")
                        .param("lastName", "Turing")
                        .param("email", "pas-un-email"))
                .andExpect(view().name("addPerson"))
                .andExpect(model().attributeHasFieldErrors("personForm", "email"));
    }

    @Test
    void validFormRedirectsToList() throws Exception {
        mvc.perform(post("/addPerson")
                        .param("firstName", "Alan")
                        .param("lastName", "Turing")
                        .param("email", "alan@turing.org"))
                .andExpect(redirectedUrl("/personList"));
    }

    @Test
    void deleteRedirectsToList() throws Exception {
        mvc.perform(post("/deletePerson").param("id", "1"))
                .andExpect(redirectedUrl("/personList"));

        verify(repository).deleteById(1L);
    }
}