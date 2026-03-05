package training.afpa.cda24060.web2026.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import training.afpa.cda24060.web2026.model.Person;

import java.util.List;

@Data
public class PersonPageResponseDTO {

    // Getters et Setters (ou utilise @Data de Lombok)
    private List<Person> content;
    private boolean empty;
    private boolean first;
    private boolean last;
    private int number;
    private int numberOfElements;
    private Pageable pageable;
    private int size;
    private Sort sort;
    private long totalElements;
    private int totalPages;

    // Classes internes pour "pageable" et "sort"
    @Data
    public static class Pageable {
        private int offset;
        private int pageNumber;
        private int pageSize;
        private boolean paged;
        private Sort sort;
        private boolean unpaged;
    }

    @Data
    public static class Sort {
        private boolean empty;
        private boolean sorted;
        private boolean unsorted;
    }
}