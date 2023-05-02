package ru.otus.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.dto.ClientDto;
import ru.otus.templates.ClientModel;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class ClientController {
    @Autowired
    private DBServiceClient dbServiceClient;

    @GetMapping("/clients")
    public String clientList(Model model) {
        List<ClientModel> clients = dbServiceClient.findAll().stream().map(ClientModel::new).collect(Collectors.toList());
        model.addAttribute("clientList", clients);

        return "clients";
    }

    @PostMapping("/api/client")
    public RedirectView saveClient(@RequestBody ClientDto dto) {
        String name = dto.getName();
        Address address = new Address(dto.getAddress(), null);
        Set<Phone> phones = new HashSet<>();

        if (StringUtils.hasText(dto.getPhone())) {
            phones = Set.of(new Phone(dto.getPhone(), null));
        }

        Client client = new Client(name, address, phones);

        dbServiceClient.saveClient(client);

        return new RedirectView("/clients", true);
    }


}
