package rs.raf.dmilutinovic10518rn.wpseptembar.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import rs.raf.dmilutinovic10518rn.wpseptembar.configuration.NewsApplication;
import rs.raf.dmilutinovic10518rn.wpseptembar.entities.*;
import rs.raf.dmilutinovic10518rn.wpseptembar.repositories.IUserRepository;

import javax.inject.Inject;
import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.HashMap;

public class UserService implements IUserService {
    @Inject
    private IUserRepository userRepository;

    @Override
    public PaginatedRecord getUsers(int pageNumber) {
        return userRepository.getUsers(pageNumber);
    }

    @Override
    public User getUserById(int id) {
        return userRepository.getUserById(id);
    }

    @Override
    public User registerUser(UserFormRegistration registrationForm) {
        if (!registrationForm.getPassword().equals(registrationForm.getConfirmPassword()))
            throw new ValidationException("Provided passwords must be the same!");

        User user = userRepository.registerUser(registrationForm);

        if (user == null)
            throw new ValidationException("The user with this email already exists");

        return user;
    }

    @Override
    public User editUser(UserFormEdit editForm) {
        if (editForm.getPassword() != null) {
            if (editForm.getConfirmPassword() == null)
                throw new ValidationException("You must confirm the password!");

            if (editForm.getPassword().length() < 5 || editForm.getPassword().length() > 25)
                throw new ValidationException("The password must be at least 5, or at most 25 characters in length!");

            if (!editForm.getPassword().equals(editForm.getConfirmPassword()))
                throw new ValidationException("Provided passwords must be the same!");
        }

        User user = userRepository.editUser(editForm);

        if (user == null)
            throw new ValidationException("You are trying to edit a non existent user!");

        return user;
    }

    @Override
    public Response login(LoginForm formLogin) {
        User user = userRepository.login(formLogin);

        if (user == null)
            throw new ValidationException("Invalid credentials!");

        if (!user.isActive())
            throw new ValidationException("Deactivated user!");

        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + 60 * 60 * 24 * 30 * 12 * 1000);

        Algorithm algorithm = Algorithm.HMAC256(NewsApplication.JWT_SECRET);

        String jwt = JWT.create()
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .withSubject(user.getEmail())
                .withClaim("role", user.isAdmin() ? "admin" : "creator")
                .sign(algorithm);

        return Response.status(200).entity(new HashMap<String, Object>() {{
            put("jwt", jwt);
            put("user", user);
        }}).build();
    }

    @Override
    public boolean isLoggedIn(String token) {
        Algorithm algorithm = Algorithm.HMAC256(NewsApplication.JWT_SECRET);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);

        User user = this.userRepository.getUserByEmail(jwt.getSubject());

        if (user == null)
            return false;

        if (!user.isActive())
            return false;

        return true;
    }

    @Override
    public boolean hasRole(String token, String role) {
        Algorithm algorithm = Algorithm.HMAC256(NewsApplication.JWT_SECRET);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);

        User user = this.userRepository.getUserByEmail(jwt.getSubject());

        if (user == null)
            return false;

        return role.equalsIgnoreCase("admin") && user.isAdmin() || role.equalsIgnoreCase("creator") && !user.isAdmin();
    }
}
