package ru.otus.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDto {
    private final String name;
    private final String address;
    private final String phone;

    public ClientDto(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }


}
