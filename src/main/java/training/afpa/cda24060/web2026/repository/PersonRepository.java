package training.afpa.cda24060.web2026.repository;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import training.afpa.cda24060.web2026.config.CustomProperty;
import training.afpa.cda24060.web2026.dto.PersonPageResponseDTO;
import training.afpa.cda24060.web2026.dto.filter.PersonFilterDTO;
import training.afpa.cda24060.web2026.model.Person;

import java.net.URI;

@Repository
@Slf4j
public class PersonRepository {

    @Autowired
    private CustomProperty property;

    private WebClient webClient;

    @PostConstruct
    public void init() {

        System.out.println("API URL = " + property.getApiURL());
        this.webClient = WebClient.builder()
                .baseUrl(property.getApiURL())
                .build();
    }

    public PersonPageResponseDTO getPersons(Pageable pageable, PersonFilterDTO filter) {
        try {
            return webClient.get()
                    .uri(uriBuilder -> {

                        uriBuilder.path("/api/persons")
                                .queryParam("page", pageable.getPageNumber())
                                .queryParam("size", pageable.getPageSize());

                        if (filter.getLastname() != null && !filter.getLastname().isEmpty()) {
                            uriBuilder.queryParam("lastname", filter.getLastname());
                        }

                        if (filter.getFirstname() != null && !filter.getFirstname().isEmpty()) {
                            uriBuilder.queryParam("firstname", filter.getFirstname());
                        }

                        pageable.getSort().forEach(order ->
                                uriBuilder.queryParam("sort",
                                        order.getProperty() + "," + order.getDirection())
                        );

                        return uriBuilder.build();
                    })
                    .retrieve()
                    .bodyToMono(PersonPageResponseDTO.class)
                    .block();
        } catch (WebClientResponseException e) {
            log.error("Erreur lors de l'appel GET /api/persons: {}", e.getMessage());
            return null;
        }
    }

    public Person getPerson(int id) {
        String uri = "/api/person/" + id;
        log.debug("Get Person URI: {}", uri);

        try {
            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(Person.class)
                    .block();
        } catch (WebClientResponseException e) {
            log.error("Erreur lors de l'appel GET /api/person/{}: {}", id, e.getMessage());
            return null;
        }
    }

    public Person createPerson(Person person) {
        String uri = "/api/person";
        log.debug("Create Person URI: {}", uri);

        try {
            return webClient.post()
                    .uri(uri)
                    .bodyValue(person)
                    .retrieve()
                    .bodyToMono(Person.class)
                    .block();
        } catch (WebClientResponseException e) {
            log.error("Erreur lors de l'appel POST /api/person: {}", e.getMessage());
            return null;
        }
    }

    public Person updatePerson(Person person) {
        String uri = "/api/person/" + person.getId();
        log.debug("Update Person URI: {}", uri);

        try {
            return webClient.put()
                    .uri(uri)
                    .bodyValue(person)
                    .retrieve()
                    .bodyToMono(Person.class)
                    .block();
        } catch (WebClientResponseException e) {
            log.error("Erreur lors de l'appel PUT /api/person/{}: {}", person.getId(), e.getMessage());
            return null;
        }
    }

    public void deletePerson(int id) {
        String uri = "/api/person/" + id;
        log.debug("Delete Person URI: {}", uri);

        try {
            webClient.delete()
                    .uri(uri)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        } catch (WebClientResponseException e) {
            log.error("Erreur lors de l'appel DELETE /api/person/{}: {}", id, e.getMessage());
        }
    }
}

