package ru.otus.services;

import org.eclipse.jetty.security.AbstractLoginService;
import org.eclipse.jetty.security.RolePrincipal;
import org.eclipse.jetty.security.UserPrincipal;
import org.eclipse.jetty.util.security.Password;
import ru.otus.dao.AdminDao;
import ru.otus.model.Admin;

import java.util.List;
import java.util.Optional;

public class InMemoryLoginServiceImpl extends AbstractLoginService {

    private final AdminDao adminDao;

    public InMemoryLoginServiceImpl(AdminDao adminDao) {
        this.adminDao = adminDao;
    }


    @Override
    protected List<RolePrincipal> loadRoleInfo(UserPrincipal userPrincipal) {
        return List.of(new RolePrincipal("admin"));
    }

    @Override
    protected UserPrincipal loadUserInfo(String login) {
        System.out.println(String.format("InMemoryAdminDao#loadUserInfo(%s)", login));
        Optional<Admin> dbUser = adminDao.findByLogin(login);
        return dbUser.map(u -> new UserPrincipal(u.getLogin(), new Password(u.getPassword()))).orElse(null);
    }
}
