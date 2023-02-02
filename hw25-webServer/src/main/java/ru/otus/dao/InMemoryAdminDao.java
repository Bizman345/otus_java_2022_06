package ru.otus.dao;

import ru.otus.model.Admin;

import java.util.*;

public class InMemoryAdminDao implements AdminDao {

    private final Map<Long, Admin> admins;

    public InMemoryAdminDao() {
        admins = new HashMap<>();
        admins.put(1L, new Admin(1L, "Админ Админский", "admin", "admin"));
        admins.put(2L, new Admin(2L, "Админ II", "admin2", "admin2"));
        admins.put(3L, new Admin(3L, "Админ III", "admin3", "admin3"));
    }

    @Override
    public Optional<Admin> findById(long id) {
        return Optional.ofNullable(admins.get(id));
    }

    @Override
    public Optional<Admin> findRandomUser() {
        Random r = new Random();
        return admins.values().stream().skip(r.nextInt(admins.size() - 1)).findFirst();
    }

    @Override
    public Optional<Admin> findByLogin(String login) {
        return admins.values().stream().filter(v -> v.getLogin().equals(login)).findFirst();
    }
}
