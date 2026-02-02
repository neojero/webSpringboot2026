package training.afpa.cda24060.web2026.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import training.afpa.cda24060.web2026.config.CustomProperty;
import training.afpa.cda24060.web2026.model.Person;

@Repository
@Slf4j
public class PersonRepository {

    @Autowired
    private CustomProperty property;

    public Iterable<Person> getPersons() {
        // recupération de ma proprietes contenant l'url de l'API
        String baseURL = property.getApiURL();
        // construction de l'url pour appel à l'API
        String getPersonsURL = baseURL + "/persons";
        // construction de la requete HTTP
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Iterable<Person>> response = restTemplate.exchange(
                getPersonsURL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );

        log.debug("Get Persons call {}", response.getStatusCode());
        // envoi de la reponse.
        return response.getBody();
    }

    public Person getPerson(int id) {
        String baseApiUrl = property.getApiURL();
        String getPersonUrl = baseApiUrl + "/person/" + id;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Person> response = restTemplate.exchange(
                getPersonUrl,
                HttpMethod.GET,
                null,
                Person.class
        );

        log.debug("Get Person call " + response.getStatusCode());

        return response.getBody();
    }

    public Person createPerson(Person person) {
        String baseApiUrl = property.getApiURL();
        String createPersonsUrl = baseApiUrl + "/person";

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Person> request = new HttpEntity<>(person);
        ResponseEntity<Person> response = restTemplate.exchange(
                createPersonsUrl,
                HttpMethod.POST,
                request,
                Person.class);

        log.debug("Create Person call " + response.getStatusCode());

        return response.getBody();
    }

    public Person updatePerson(Person person) {
        String baseApiUrl = property.getApiURL();
        String updatePersonUrl = baseApiUrl + "/person/" + person.getId();

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Person> request = new HttpEntity<>(person);
        ResponseEntity<Person> response = restTemplate.exchange(
                updatePersonUrl,
                HttpMethod.PUT,
                request,
                Person.class);

        log.debug("Update Person call " + response.getStatusCode());

        return response.getBody();
    }

    public void deletePerson(int id) {
        String baseApiUrl = property.getApiURL();
        String deletePersonUrl = baseApiUrl + "/person/" + id;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Void> response = restTemplate.exchange(
                deletePersonUrl,
                HttpMethod.DELETE,
                null,
                Void.class);

        log.debug("Delete Person call " + response.getStatusCode());
    }


}
