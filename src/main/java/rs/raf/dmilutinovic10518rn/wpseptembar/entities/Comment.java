package rs.raf.dmilutinovic10518rn.wpseptembar.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class Comment {
    private int id;

    @NotEmpty(message = "The comment author name can't be empty!")
    @NotNull(message = "The comment author name can't be empty!")
    private String author;

    @NotEmpty(message = "The comment content can't be empty!")
    @NotNull(message = "The comment content can't be empty!")
    private String content;

    private Date time;

    private int likes;
    private int dislikes;

    public Comment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }
}
