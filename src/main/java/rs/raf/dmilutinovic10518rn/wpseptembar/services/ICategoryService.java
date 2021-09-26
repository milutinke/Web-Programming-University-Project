package rs.raf.dmilutinovic10518rn.wpseptembar.services;

import rs.raf.dmilutinovic10518rn.wpseptembar.entities.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> getCategories();

    Category getCategory(int id);

    Category addCategory(Category category);

    boolean editCategory(Category category);

    boolean deleteCategory(int id);
}
