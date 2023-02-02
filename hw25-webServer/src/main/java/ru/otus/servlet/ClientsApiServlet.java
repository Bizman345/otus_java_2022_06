package ru.otus.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import org.eclipse.jetty.util.StringUtil;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.model.Admin;
import ru.otus.dao.AdminDao;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.model.ClientDto;

import java.io.IOException;
import java.util.List;


public class ClientsApiServlet extends HttpServlet {

    private static final int ID_PATH_PARAM_POSITION = 1;

    private final DBServiceClient clientService;
    private final Gson gson;

    public ClientsApiServlet(DBServiceClient clientService, Gson gson) {
        this.clientService = clientService;
        this.gson = gson;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ClientDto clientDto = gson.fromJson(req.getReader(), ClientDto.class);

        Client client = buildClient(clientDto);

        clientService.saveClient(client);

        resp.setContentType("application/json;charset=UTF-8");
        resp.setStatus(200);
    }

    private Client buildClient(ClientDto clientDto) {
        Client client = new Client();

        client.setName(clientDto.getName());

        if (StringUtil.isNotBlank(clientDto.getAddress())) {
            Address address = new Address();
            address.setStreet(clientDto.getAddress());
            client.setAddress(address);
        }

        if (StringUtil.isNotBlank(clientDto.getPhone())) {
            Phone phone = new Phone();
            phone.setNumber(clientDto.getPhone());
            client.setPhone(List.of(phone));
        }

        return client;
    }

    private long extractIdFromRequest(HttpServletRequest request) {
        String[] path = request.getPathInfo().split("/");
        String id = (path.length > 1)? path[ID_PATH_PARAM_POSITION]: String.valueOf(- 1);
        return Long.parseLong(id);
    }

}
