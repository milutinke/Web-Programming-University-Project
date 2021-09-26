package rs.raf.dmilutinovic10518rn.wpseptembar.repositories;

import rs.raf.dmilutinovic10518rn.wpseptembar.entities.*;

public interface IUserRepository {
    PaginatedRecord getUsers(int pageNumber);

    User getUserById(int id);

    User getUserByEmail(String email);

    User registerUser(UserFormRegistration user);

    User editUser(UserFormEdit user);

    User login(LoginForm user);
}
