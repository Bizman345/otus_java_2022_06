package ru.otus.services;

import ru.otus.dao.AdminDao;

public class UserAuthServiceImpl implements UserAuthService {

    private final AdminDao adminDao;

    public UserAuthServiceImpl(AdminDao adminDao) {
        this.adminDao = adminDao;
    }

    @Override
    public boolean authenticate(String login, String password) {
        return adminDao.findByLogin(login)
                .map(user -> user.getPassword().equals(password))
                .orElse(false);
    }

}
