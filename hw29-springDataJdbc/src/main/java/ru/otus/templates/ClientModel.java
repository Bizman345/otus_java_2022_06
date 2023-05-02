package ru.otus.templates;

import lombok.Getter;
import lombok.Setter;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;

import java.util.stream.Collectors;

@Getter
@Setter
public class ClientModel {
    private Long id;

    private String name;

    private String address;

    private String phones;

    public ClientModel(Client client) {
        this.id = client.getId();
        this.name = client.getName();
        this.address = client.getAddress().getStreet();
        this.phones = client.getPhones().stream().map(Phone::getNumber).collect(Collectors.joining(";"));
    }
}
