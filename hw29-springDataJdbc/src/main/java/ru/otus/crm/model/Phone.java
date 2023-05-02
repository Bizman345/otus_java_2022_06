package ru.otus.crm.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@Table("phone")
public class Phone {
    @Id
    private Long id;

    private String number;

    private Long clientId;

    public Phone(String number, Long clientId) {
        this(null, number, clientId);
    }

    @PersistenceConstructor
    public Phone(Long id, String number, Long clientId) {
        this.id = id;
        this.number = number;
        this.clientId = clientId;
    }
}
