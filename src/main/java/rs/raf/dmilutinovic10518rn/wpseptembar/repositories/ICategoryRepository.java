package rs.raf.dmilutinovic10518rn.wpseptembar.repositories;

import rs.raf.dmilutinovic10518rn.wpseptembar.entities.Category;
import rs.raf.dmilutinovic10518rn.wpseptembar.entities.PaginatedRecord;

import java.util.List;

public interface ICategoryRepository {
    List<Category> getCategories();

    Category getCategory(int id);

    Category addCategory(Category category);

    boolean editCategory(Category category);

    boolean deleteCategory(int id);
}
