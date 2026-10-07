package local.epul4a.demosbt.controller;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import jakarta.validation.Valid;
import local.epul4a.demosbt.form.PersonForm;
import local.epul4a.demosbt.model.Person;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    // Données en mémoire (démonstration). Le contrôleur est un singleton
    // partagé entre les requêtes : on utilise donc une liste thread-safe.
    private final List<Person> persons = new CopyOnWriteArrayList<>();

    private final String message;

    // Injection par constructeur de la valeur définie dans application.properties
    public MainController(@Value("${welcome.message}") String message) {
        this.message = message;
        persons.add(new Person("Bill", "Gates"));
        persons.add(new Person("Steve", "Jobs"));
    }

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        model.addAttribute("message", message);
        return "index";
    }

    @GetMapping("/personList")
    public String personList(Model model) {
        model.addAttribute("persons", persons);
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
            return "addPerson"; // on réaffiche le formulaire avec les erreurs
        }
        persons.add(new Person(personForm.getFirstName(), personForm.getLastName()));
        return "redirect:/personList"; // Post/Redirect/Get
    }
}