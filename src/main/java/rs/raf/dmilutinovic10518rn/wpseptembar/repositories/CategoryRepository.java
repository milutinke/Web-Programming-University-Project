package rs.raf.dmilutinovic10518rn.wpseptembar.repositories;

import rs.raf.dmilutinovic10518rn.wpseptembar.entities.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository extends AbstractMySQLRepository implements ICategoryRepository {
    @Override
    public List<Category> getCategories() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Category> categories = new ArrayList<Category>();

        try {
            connection = newConnection();

            preparedStatement = connection.prepareStatement("SELECT id, title, description FROM categories");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getInt("id"));
                category.setTitle(resultSet.getString("title"));
                category.setDescription(resultSet.getString("description"));
                categories.add(category);
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

        return categories;
    }

    @Override
    public Category getCategory(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Category category = null;

        try {
            connection = newConnection();

            preparedStatement = connection.prepareStatement("SELECT id, title, description FROM categories WHERE id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                category = new Category();
                category.setId(resultSet.getInt("id"));
                category.setTitle(resultSet.getString("title"));
                category.setDescription(resultSet.getString("description"));
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

        return category;
    }

    @Override
    public Category addCategory(Category category) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Category result = null;

        try {
            connection = newConnection();

            preparedStatement = connection.prepareStatement("SELECT id FROM categories WHERE title = ?");
            preparedStatement.setString(1, category.getTitle());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                this.closeStatement(preparedStatement);
                this.closeResultSet(resultSet);
                this.closeConnection(connection);
                return result;
            }

            preparedStatement = connection.prepareStatement("INSERT INTO categories (title, description) VALUES(?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, category.getTitle());
            preparedStatement.setString(2, category.getDescription());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                result = new Category();
                result.setId(resultSet.getInt(1));
                result.setTitle(result.getTitle());
                result.setDescription(result.getDescription());
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
    public boolean editCategory(Category category) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean result = false;

        if (this.getCategory(category.getId()) == null)
            return result;

        try {
            connection = newConnection();

            preparedStatement = connection.prepareStatement("SELECT id FROM categories WHERE title = ? AND id <> ?");
            preparedStatement.setString(1, category.getTitle());
            preparedStatement.setInt(2, category.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                this.closeStatement(preparedStatement);
                this.closeResultSet(resultSet);
                this.closeConnection(connection);
                return result;
            }

            preparedStatement = connection.prepareStatement("UPDATE categories SET title = ?, description = ? WHERE id = ?");
            preparedStatement.setString(1, category.getTitle());
            preparedStatement.setString(2, category.getDescription());
            preparedStatement.setInt(3, category.getId());
            preparedStatement.executeUpdate();

            result = true;
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
    public boolean deleteCategory(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean result = false;

        if (this.getCategory(id) == null)
            return result;

        try {
            connection = newConnection();

            preparedStatement = connection.prepareStatement("SELECT id FROM news WHERE categoryId = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                this.closeStatement(preparedStatement);
                this.closeResultSet(resultSet);
                this.closeConnection(connection);
                return result;
            }

            preparedStatement = connection.prepareStatement("DELETE FROM categories WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            result = true;
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
