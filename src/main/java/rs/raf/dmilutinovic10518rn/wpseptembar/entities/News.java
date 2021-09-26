package rs.raf.dmilutinovic10518rn.wpseptembar.entities;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class News {
    private int id;

    @NotNull(message = "You must provide the title!")
    @NotEmpty(message = "You must provide the title!")
    @Length(min = 5, max = 125, message = "The title must be at least 5, or at most 128 characters long!")
    private String title;

    @NotNull(message = "You must provide the content!")
    @NotEmpty(message = "You must provide the content!")
    @Length(min = 5, message = "The content must be at least 5 characters long!")
    private String content;
    private Date time;
    private int visits;

    @NotNull(message = "You must provide the author id!")
    private int authorId;

    private String author;

    @NotNull(message = "You must provide the category id!")
    private int categoryId;

    private String category;
    private String tags;

    public News() {
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getVisits() {
        return visits;
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
