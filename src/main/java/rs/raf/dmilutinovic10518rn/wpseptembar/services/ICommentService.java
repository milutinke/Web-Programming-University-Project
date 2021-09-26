package rs.raf.dmilutinovic10518rn.wpseptembar.services;

import rs.raf.dmilutinovic10518rn.wpseptembar.entities.Comment;

import java.util.List;

public interface ICommentService {
    List<Comment> getNewsComments(int newsId);

    Comment addComment(Comment comment);

    int interactWithComment(int commentId, int interaction);
}
