package rs.raf.dmilutinovic10518rn.wpseptembar.resources;

import rs.raf.dmilutinovic10518rn.wpseptembar.entities.Category;
import rs.raf.dmilutinovic10518rn.wpseptembar.services.ICategoryService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/category")
public class ProtectedCategoryResource {
    @Inject
    private ICategoryService categoryService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Category addCategory(@Valid Category category) {
        Category result = categoryService.addCategory(category);

        if (result == null)
            throw new ValidationException("Category with this title already exists!");

        return result;
    }

    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public boolean editCategory(@PathParam("id") int id, @Valid Category category) {
        category.setId(id);
        return categoryService.editCategory(category);
    }

    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{id}")
    public boolean deleteCategory(@PathParam("id") int id) {
        return categoryService.deleteCategory(id);
    }
}
