package rs.raf.dmilutinovic10518rn.wpseptembar.services;

import rs.raf.dmilutinovic10518rn.wpseptembar.entities.Category;
import rs.raf.dmilutinovic10518rn.wpseptembar.repositories.ICategoryRepository;

import javax.inject.Inject;
import java.util.List;

public class CategoryService implements ICategoryService {
    @Inject
    private ICategoryRepository categoryRepository;

    @Override
    public List<Category> getCategories() {
        return categoryRepository.getCategories();
    }

    @Override
    public Category getCategory(int id) {
        return categoryRepository.getCategory(id);
    }

    @Override
    public Category addCategory(Category category) {
        return categoryRepository.addCategory(category);
    }

    @Override
    public boolean editCategory(Category category) {
        return categoryRepository.editCategory(category);
    }

    @Override
    public boolean deleteCategory(int id) {
        return categoryRepository.deleteCategory(id);
    }
}
