package training.afpa.cda24060.web2026.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import training.afpa.cda24060.web2026.config.CustomProperty;
import training.afpa.cda24060.web2026.dto.PersonPageResponseDTO;
import training.afpa.cda24060.web2026.dto.filter.PersonFilterDTO;
import training.afpa.cda24060.web2026.model.Person;

import java.util.stream.Collectors;

@Repository
@Slf4j
public class PersonRepository {

    @Autowired
    private CustomProperty property;

    public PersonPageResponseDTO getPersons(Pageable pageable, PersonFilterDTO filter) {
        String baseURL = property.getApiURL();
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseURL + "/api/persons")
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize());

        // Ajouter les paramètres de filtre si non vides
        if (filter.getLastname() != null && !filter.getLastname().isEmpty()) {
            builder.queryParam("lastname", filter.getLastname());
        }
        if (filter.getFirstname() != null && !filter.getFirstname().isEmpty()) {
            builder.queryParam("firstname", filter.getFirstname());
        }

        pageable.getSort().forEach(order -> {
            builder.queryParam("sort", order.getProperty() + "," + order.getDirection());
        });

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<PersonPageResponseDTO> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                null,
                PersonPageResponseDTO.class
        );

        log.debug("Get Persons call {}", response.getStatusCode());
        return response.getBody();
    }

    public Person getPerson(int id) {
        String baseApiUrl = property.getApiURL();
        String getPersonUrl = baseApiUrl + "/api/person/" + id;

        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<Person> response = restTemplate.exchange(
                    getPersonUrl,
                    HttpMethod.GET,
                    null,
                    Person.class
            );
            log.debug("Get Person call " + response.getStatusCode());
            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // Récupère le code de statut
            HttpStatus statusCode = (HttpStatus) e.getStatusCode();
            log.error("Erreur lors de l'appel à l'API : {}", statusCode);
            // Gérer l'erreur (ex : retourner null, lancer une exception personnalisée, etc.)
            return null;
        }
    }

    public Person createPerson(Person person) {
        String baseApiUrl = property.getApiURL();
        String createPersonsUrl = baseApiUrl + "/api/person";

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
        String updatePersonUrl = baseApiUrl + "/api/person/" + person.getId();

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
        String deletePersonUrl = baseApiUrl + "/api/person/" + id;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Void> response = restTemplate.exchange(
                deletePersonUrl,
                HttpMethod.DELETE,
                null,
                Void.class);

        log.debug("Delete Person call " + response.getStatusCode());
    }


}
