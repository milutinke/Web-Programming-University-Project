package rs.raf.dmilutinovic10518rn.wpseptembar.services;

import rs.raf.dmilutinovic10518rn.wpseptembar.entities.*;

import javax.ws.rs.core.Response;

public interface IUserService {
    PaginatedRecord getUsers(int pageNumber);

    User getUserById(int id);

    User registerUser(UserFormRegistration formRegistration);

    User editUser(UserFormEdit formEdit);

    Response login(LoginForm formLogin);

    public boolean isLoggedIn(String token);

    public boolean hasRole(String token, String role);
}
