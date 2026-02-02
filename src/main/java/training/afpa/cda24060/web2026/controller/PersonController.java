package training.afpa.cda24060.web2026.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import training.afpa.cda24060.web2026.model.Person;
import training.afpa.cda24060.web2026.service.PersonService;

@Controller
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping(value = {"/", "/home"})
    public String home(Model model) {
        Iterable<Person> listPersons = personService.getAllPersons();
        model.addAttribute("listPersons", listPersons);
        return "home";
    }

    @GetMapping(value = {"/createperson"})
    public String createPerson(Model model) {
        Person person = new Person();
        model.addAttribute("person", person);
        return "createperson";
    }

    @GetMapping(value = {"/updateperson/{id}"})
    public String updatePerson(@PathVariable("id") final Integer id, Model model) {
        Person person = personService.getPerson(id);
        model.addAttribute("person", person);
        return "updateperson";
    }

    @GetMapping(value = {"/deleteperson/{id}"})
    public ModelAndView deletePerson(@PathVariable("id") final Integer id) {
        personService.detelePerson(id);
        // redirection vers la page home
        return new ModelAndView("redirect:/");
    }

    @PostMapping(value = {"/saveperson"})
    public ModelAndView savePerson(@ModelAttribute Person person) {
        personService.savePerson(person);
        // redirection vers la page home
        return new ModelAndView("redirect:/");
    }

}
