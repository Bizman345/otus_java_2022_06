package ru.otus.crm.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("address")
public class Address  {

    @Id
    private Long id;

    private String street;

    private Long clientId;

    public Address(String street, Long clientId) {
        this(null, clientId, street);
    }

    @PersistenceConstructor
    public Address(Long id, Long clientId, String street) {
        this.id = id;
        this.street = street;
    }
}
