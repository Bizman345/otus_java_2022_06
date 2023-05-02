package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.otus.crm.repository.ClientRepository;
import ru.otus.crm.service.DBServiceClient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


@Component("actionDemo")
public class ActionDemo {
    private static final Logger log = LoggerFactory.getLogger(ActionDemo.class);

    private final ClientRepository clientRepository;
    private final DBServiceClient dbServiceClient;

    public ActionDemo(ClientRepository clientRepository,
                      DBServiceClient dbServiceClient) {
        this.clientRepository = clientRepository;
        this.dbServiceClient = dbServiceClient;
    }

    void action() {

    }
}
