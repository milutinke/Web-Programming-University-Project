package rs.raf.dmilutinovic10518rn.wpseptembar.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Category {
    private int id;

    @NotNull(message = "You must provide a category title")
    @NotEmpty(message = "You must provide a category title")
    @Size(min = 5, max = 32, message = "The category title must be at least 5, or at most 32 characters long")
    private String title;

    @NotNull(message = "You must provide a category description")
    @NotEmpty(message = "You must provide a category description")
    @Size(min = 10, max = 64, message = "The category description must be at least 10, or at most 64 characters long")
    private String description;

    public Category() {
    }

    public Category(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Category(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
