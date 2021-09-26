package rs.raf.dmilutinovic10518rn.wpseptembar.services;

import rs.raf.dmilutinovic10518rn.wpseptembar.entities.Comment;
import rs.raf.dmilutinovic10518rn.wpseptembar.repositories.ICommentsRepository;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;

public class CommentService implements ICommentService {
    @Inject
    private ICommentsRepository commentsRepository;

    @Override
    public List<Comment> getNewsComments(int newsId) {
        return commentsRepository.getNewsComments(newsId);
    }

    @Override
    public Comment addComment(@Valid Comment comment) {
        return commentsRepository.addComment(comment);
    }

    @Override
    public int interactWithComment(int commentId, int interaction) {
        return commentsRepository.interactWithComment(commentId, interaction);
    }
}
