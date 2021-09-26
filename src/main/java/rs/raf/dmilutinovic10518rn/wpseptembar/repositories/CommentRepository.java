package rs.raf.dmilutinovic10518rn.wpseptembar.repositories;

import rs.raf.dmilutinovic10518rn.wpseptembar.entities.Comment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentRepository extends AbstractMySQLRepository implements ICommentsRepository {
    @Override
    public List<Comment> getNewsComments(int newsId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Comment> comments = new ArrayList<Comment>();

        try {
            connection = newConnection();

            // TODO: Like/Dislike
            preparedStatement = connection.prepareStatement("SELECT id, author, content, time FROM comments WHERE newsId = ?");
            preparedStatement.setInt(1, newsId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Comment comment = new Comment();
                comment.setId(resultSet.getInt("id"));
                comment.setAuthor(resultSet.getString("author"));
                comment.setContent(resultSet.getString("content"));
                comment.setTime(resultSet.getDate("time"));
                comments.add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null)
                this.closeStatement(preparedStatement);

            if (resultSet != null)
                this.closeResultSet(resultSet);

            if (connection != null)
                this.closeConnection(connection);
        }

        return comments;
    }

    @Override
    public Comment addComment(Comment comment) {
        return null;
    }

    @Override
    public int interactWithComment(int commentId, int interaction) {
        return 0;
    }
}
