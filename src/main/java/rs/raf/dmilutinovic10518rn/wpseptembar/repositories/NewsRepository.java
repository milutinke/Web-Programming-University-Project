package rs.raf.dmilutinovic10518rn.wpseptembar.repositories;

import rs.raf.dmilutinovic10518rn.wpseptembar.entities.News;
import rs.raf.dmilutinovic10518rn.wpseptembar.entities.PaginatedRecord;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NewsRepository extends AbstractMySQLRepository implements INewsRepository {
    @Override
    public PaginatedRecord getNews(int pageNumber) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Object> data = new ArrayList<Object>();
        int total = 0;

        try {
            connection = newConnection();

            preparedStatement = connection.prepareStatement("SELECT count(*) FROM news");
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
                total = resultSet.getInt(1);

            preparedStatement = connection.prepareStatement("SELECT n.id as newsId, n.title as newsTitle, content, time, CONCAT_WS(' ', u.firstName, u.lastName) as author, authorId, categoryId, c.title as category, (SELECT count(*) FROM visits v WHERE v.newsId = n.id) as visits FROM news n JOIN users u ON n.authorId = u.id JOIN categories c ON n.categoryId = c.id ORDER BY time DESC LIMIT 10 OFFSET ?");
            preparedStatement.setInt(1, (pageNumber - 1) * 10);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                News news = new News();
                news.setId(resultSet.getInt("newsId"));
                news.setTitle(resultSet.getString("newsTitle"));
                news.setContent(resultSet.getString("content"));
                news.setTime(resultSet.getDate("time"));
                news.setAuthorId(resultSet.getInt("authorId"));
                news.setAuthor(resultSet.getString("author"));
                news.setCategoryId(resultSet.getInt("categoryId"));
                news.setCategory(resultSet.getString("category"));
                news.setVisits(resultSet.getInt("visits"));

                data.add(news);
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

        return new PaginatedRecord(data, total, pageNumber, 10, (int) Math.ceil((double) total / 10));
    }

    @Override
    public PaginatedRecord searchNews(int pageNumber, String query) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Object> data = new ArrayList<Object>();
        int total = 0;

        try {
            connection = newConnection();

            String wildcard = "%" + query + "%";
            preparedStatement = connection.prepareStatement("SELECT count(*) FROM news WHERE title LIKE ? OR content LIKE ?");
            preparedStatement.setString(1, wildcard);
            preparedStatement.setString(2, wildcard);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
                total = resultSet.getInt(1);

            preparedStatement = connection.prepareStatement("SELECT n.id as newsId, n.title as newsTitle, content, time, CONCAT_WS(' ', u.firstName, u.lastName) as author, authorId, categoryId, c.title as category, (SELECT count(*) FROM visits v WHERE v.newsId = n.id) as visits FROM news n JOIN users u ON n.authorId = u.id JOIN categories c ON n.categoryId = c.id WHERE n.title LIKE ? OR n.content LIKE ? LIMIT 10 OFFSET ?");
            preparedStatement.setString(1, wildcard);
            preparedStatement.setString(2, wildcard);
            preparedStatement.setInt(3, (pageNumber - 1) * 10);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                News news = new News();
                news.setId(resultSet.getInt("newsId"));
                news.setTitle(resultSet.getString("newsTitle"));
                news.setContent(resultSet.getString("content"));
                news.setTime(resultSet.getDate("time"));
                news.setAuthorId(resultSet.getInt("authorId"));
                news.setAuthor(resultSet.getString("author"));
                news.setCategoryId(resultSet.getInt("categoryId"));
                news.setCategory(resultSet.getString("category"));
                news.setVisits(resultSet.getInt("visits"));

                data.add(news);
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

        return new PaginatedRecord(data, total, pageNumber, 10, (int) Math.ceil((double) total / 10));
    }

    @Override
    public List<News> getHomePageNews() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<News> data = new ArrayList<News>();

        try {
            connection = newConnection();

            preparedStatement = connection.prepareStatement("SELECT n.id as newsId, n.title as newsTitle, content, time, CONCAT_WS(' ', u.firstName, u.lastName) as author, authorId, categoryId, c.title as category, (SELECT count(*) FROM visits v WHERE v.newsId = n.id) as visits FROM news n JOIN users u ON n.authorId = u.id JOIN categories c ON n.categoryId = c.id ORDER BY n.time DESC LIMIT 10");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                News news = new News();
                news.setId(resultSet.getInt("newsId"));
                news.setTitle(resultSet.getString("newsTitle"));
                news.setContent(resultSet.getString("content"));
                news.setTime(resultSet.getDate("time"));
                news.setAuthorId(resultSet.getInt("authorId"));
                news.setAuthor(resultSet.getString("author"));
                news.setCategoryId(resultSet.getInt("categoryId"));
                news.setCategory(resultSet.getString("category"));
                news.setVisits(resultSet.getInt("visits"));

                data.add(news);
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

        return data;
    }

    @Override
    public List<News> getMostReadNews() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<News> data = new ArrayList<News>();

        try {
            connection = newConnection();

            preparedStatement = connection.prepareStatement("SELECT n.id as newsId, n.title as newsTitle, content, time, CONCAT_WS(' ', u.firstName, u.lastName) as author, authorId, categoryId, c.title as category, (SELECT count(*) FROM visits v WHERE v.newsId = n.id) as visits FROM news n JOIN users u ON n.authorId = u.id JOIN categories c ON n.categoryId = c.id WHERE n.time > timestampadd(day, -30, NOW()) ORDER BY visits DESC LIMIT 10");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                News news = new News();
                news.setId(resultSet.getInt("newsId"));
                news.setTitle(resultSet.getString("newsTitle"));
                news.setContent(resultSet.getString("content"));
                news.setTime(resultSet.getDate("time"));
                news.setAuthorId(resultSet.getInt("authorId"));
                news.setAuthor(resultSet.getString("author"));
                news.setCategoryId(resultSet.getInt("categoryId"));
                news.setCategory(resultSet.getString("category"));
                news.setVisits(resultSet.getInt("visits"));

                data.add(news);
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

        return data;
    }

    @Override
    public News getSingle(String visitor, int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        News news = null;

        try {
            connection = newConnection();

            preparedStatement = connection.prepareStatement("SELECT id FROM news WHERE id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                this.closeStatement(preparedStatement);
                this.closeResultSet(resultSet);
                this.closeConnection(connection);

                return null;
            }

            preparedStatement = connection.prepareStatement("SELECT * FROM visits WHERE newsId = ? AND visitor = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, visitor);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                preparedStatement = connection.prepareStatement("INSERT INTO visits (newsId, visitor) VALUES(?, ?)");
                preparedStatement.setInt(1, id);
                preparedStatement.setString(2, visitor);
                preparedStatement.executeUpdate();
            }

            preparedStatement = connection.prepareStatement("SELECT n.id as newsId, n.title as newsTitle, content, time, CONCAT_WS(' ', u.firstName, u.lastName) as author, authorId, categoryId, c.title as category, (SELECT count(*) FROM visits v WHERE v.newsId = n.id) as visits FROM news n JOIN users u ON n.authorId = u.id JOIN categories c ON n.categoryId = c.id WHERE n.id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                news = new News();

                news.setId(resultSet.getInt("newsId"));
                news.setTitle(resultSet.getString("newsTitle"));
                news.setContent(resultSet.getString("content"));
                news.setTime(resultSet.getDate("time"));
                news.setAuthorId(resultSet.getInt("authorId"));
                news.setAuthor(resultSet.getString("author"));
                news.setCategoryId(resultSet.getInt("categoryId"));
                news.setCategory(resultSet.getString("category"));
                news.setVisits(resultSet.getInt("visits"));
                news.setTags(getNewsTags(id));
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

        return news;
    }

    @Override
    public News postNews(News news) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        News resultNews = null;

        try {
            connection = newConnection();

            preparedStatement = connection.prepareStatement("SELECT id FROM news WHERE title = ?");
            preparedStatement.setString(1, news.getTitle());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                this.closeStatement(preparedStatement);
                this.closeResultSet(resultSet);
                this.closeConnection(connection);

                return null;
            }

            preparedStatement = connection.prepareStatement("INSERT INTO news (title, content, authorId, categoryId) VALUES(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, news.getTitle());
            preparedStatement.setString(2, news.getContent());
            preparedStatement.setInt(3, news.getAuthorId());
            preparedStatement.setInt(4, news.getCategoryId());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                resultNews = new News();
                resultNews.setId(resultSet.getInt(1));
                resultNews.setTitle(news.getTitle());
                resultNews.setContent(news.getContent());

                if (news.getTags().trim().length() > 0) {
                    for (String tag : Arrays.stream(news.getTags().split(",")).map(tag -> tag.trim()).collect(Collectors.toList())) {
                        preparedStatement = connection.prepareStatement("SELECT * FROM tags WHERE LOWER(name) = LOWER(?)");
                        preparedStatement.setString(1, tag);
                        resultSet = preparedStatement.executeQuery();

                        if (!resultSet.next()) {
                            preparedStatement = connection.prepareStatement("INSERT INTO tags (name) VALUES (?)");
                            preparedStatement.setString(1, tag);
                            preparedStatement.executeUpdate();
                        }

                        preparedStatement = connection.prepareStatement("INSERT INTO news_tags (newsId, tagId) VALUES (?, (SELECT id FROM tags WHERE LOWER(name) = LOWER(?)))");
                        preparedStatement.setInt(1, resultNews.getId());
                        preparedStatement.setString(2, tag);
                        preparedStatement.executeUpdate();
                    }
                }
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

        return resultNews;
    }

    private String getNewsTags(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            connection = newConnection();

            preparedStatement = connection.prepareStatement("SELECT t.name as tagName FROM news_tags nt JOIN tags t ON nt.tagId = t.id WHERE nt.newsId = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                stringBuilder.append(resultSet.getString("tagName"));
                stringBuilder.append(",");
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

        String temp = stringBuilder.toString();

        if (temp.length() == 0)
            return "";

        return temp.substring(0, temp.lastIndexOf(",")).trim();
    }

    @Override
    public boolean editNews(News news) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean result = false;

        try {
            connection = newConnection();

            preparedStatement = connection.prepareStatement("SELECT id FROM news WHERE title = ? AND id <> ?");
            preparedStatement.setString(1, news.getTitle());
            preparedStatement.setInt(2, news.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                this.closeStatement(preparedStatement);
                this.closeResultSet(resultSet);
                this.closeConnection(connection);

                return result;
            }

            preparedStatement = connection.prepareStatement("UPDATE news SET title = ?, content = ?, categoryId = ? WHERE id = ?");
            preparedStatement.setString(1, news.getTitle());
            preparedStatement.setString(2, news.getContent());
            preparedStatement.setInt(3, news.getCategoryId());
            preparedStatement.setInt(4, news.getId());
            preparedStatement.executeUpdate();

            result = true;

            preparedStatement = connection.prepareStatement("DELETE FROM news_tags WHERE newsId = ?");
            preparedStatement.setInt(1, news.getId());
            preparedStatement.executeUpdate();

            if (news.getTags().trim().length() > 0) {
                for (String tag : Arrays.stream(news.getTags().split(",")).map(tag -> tag.trim()).collect(Collectors.toList())) {
                    preparedStatement = connection.prepareStatement("SELECT * FROM tags WHERE LOWER(name) = LOWER(?)");
                    preparedStatement.setString(1, tag);
                    resultSet = preparedStatement.executeQuery();

                    if (!resultSet.next()) {
                        preparedStatement = connection.prepareStatement("INSERT INTO tags (name) VALUES (?)");
                        preparedStatement.setString(1, tag);
                        preparedStatement.executeUpdate();
                    }

                    preparedStatement = connection.prepareStatement("INSERT INTO news_tags (newsId, tagId) VALUES (?, (SELECT id FROM tags WHERE LOWER(name) = LOWER(?)))");
                    preparedStatement.setInt(1, news.getId());
                    preparedStatement.setString(2, tag);
                    preparedStatement.executeUpdate();
                }
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

        return result;
    }

    @Override
    public boolean deleteNews(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean result = false;

        try {
            connection = newConnection();

            preparedStatement = connection.prepareStatement("SELECT id FROM news WHERE id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                result = true;

                preparedStatement = connection.prepareStatement("DELETE FROM news_tags WHERE newsId = ?");
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();

                preparedStatement = connection.prepareStatement("DELETE FROM visits WHERE newsId = ?");
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();

                preparedStatement = connection.prepareStatement("DELETE FROM news WHERE id = ?");
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
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

        return result;
    }
}
