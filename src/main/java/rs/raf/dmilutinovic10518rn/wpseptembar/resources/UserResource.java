package rs.raf.dmilutinovic10518rn.wpseptembar.resources;

import rs.raf.dmilutinovic10518rn.wpseptembar.entities.*;
import rs.raf.dmilutinovic10518rn.wpseptembar.services.IUserService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
public class UserResource {
    @Inject
    private IUserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all/{page}")
    public PaginatedRecord getUsers(@PathParam("page") int page) {
        return userService.getUsers(page);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public User getUserById(@PathParam("id") int id) {
        return userService.getUserById(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public User registerUser(@Valid UserFormRegistration registrationForm) {
        return userService.registerUser(registrationForm);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public User editUser(@Valid UserFormEdit editForm) {
        return userService.editUser(editForm);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/login")
    public Response login(@Valid LoginForm loginForm) {
        return userService.login(loginForm);
    }
}