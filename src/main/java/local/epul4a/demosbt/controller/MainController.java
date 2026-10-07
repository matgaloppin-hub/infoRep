package local.epul4a.demosbt.controller;

import jakarta.validation.Valid;
import local.epul4a.demosbt.form.PersonForm;
import local.epul4a.demosbt.model.Person;
import local.epul4a.demosbt.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    private final PersonRepository repository;
    private final String message;

    public MainController(PersonRepository repository,
                          @Value("${welcome.message}") String message) {
        this.repository = repository;
        this.message = message;
    }

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        model.addAttribute("message", message);
        return "index";
    }

    @GetMapping("/personList")
    public String personList(Model model) {
        model.addAttribute("persons", repository.findAll());
        return "personList";
    }

    @GetMapping("/addPerson")
    public String showAddPersonPage(Model model) {
        model.addAttribute("personForm", new PersonForm());
        return "addPerson";
    }

    @PostMapping("/addPerson")
    public String savePerson(@Valid @ModelAttribute("personForm") PersonForm personForm,
                             BindingResult result) {
        if (result.hasErrors()) {
            return "addPerson";
        }
        repository.save(new Person(personForm.getFirstName(),
                personForm.getLastName(),
                personForm.getEmail()));
        return "redirect:/personList";
    }

    @PostMapping("/deletePerson")
    public String deletePerson(@RequestParam Long id) {
        repository.deleteById(id);
        return "redirect:/personList";
    }
}