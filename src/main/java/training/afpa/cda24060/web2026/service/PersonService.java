package training.afpa.cda24060.web2026.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import training.afpa.cda24060.web2026.model.Person;
import training.afpa.cda24060.web2026.repository.PersonRepository;

@Data
@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Person getPerson(Integer id) {
        // appel au repository
        return personRepository.getPerson(id);
    }

    public Iterable<Person> getAllPersons() {
        return personRepository.getPersons();
    }

    public void detelePerson(final Integer id) {
        personRepository.deletePerson(id);
    }

    public Person savePerson(Person person) {
        Person saved;
        //System.out.println(person.toString());
        // Règle de gestion : Le nom de famille doit être mis en majuscule.
        person.setLastname(person.getLastname().toUpperCase());

        if(person.getId() == null) {
            // Si l'id est nul, alors c'est un nouvel employé.
            saved = personRepository.createPerson(person);
        } else {
            // sinon c'est une mise à jour.
            saved = personRepository.updatePerson(person);
        }
        return saved;
    }

}
