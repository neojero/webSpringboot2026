package training.afpa.cda24060.web2026.model;

import lombok.Data;

import java.util.List;

@Data
public class Person {

    private Integer id;
    private String firstname;
    private String lastname;
    private Address address;
}
