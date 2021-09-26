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
public class CategoryResource {
    @Inject
    private ICategoryService categoryService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Category> getCategories() {
        return categoryService.getCategories();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Category getCategory(@PathParam("id") int id) {
        Category category = categoryService.getCategory(id);

        if (category == null)
            throw new ValidationException("Category not found!");

        return category;
    }
}
