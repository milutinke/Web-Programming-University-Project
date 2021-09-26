package rs.raf.dmilutinovic10518rn.wpseptembar.repositories;

import rs.raf.dmilutinovic10518rn.wpseptembar.entities.Comment;

import java.util.List;

public interface ICommentsRepository {
    List<Comment> getNewsComments(int newsId);

    Comment addComment(Comment comment);

    int interactWithComment(int commentId, int interaction);
}
