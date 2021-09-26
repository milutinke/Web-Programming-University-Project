package rs.raf.dmilutinovic10518rn.wpseptembar.repositories;

import at.favre.lib.crypto.bcrypt.BCrypt;
import rs.raf.dmilutinovic10518rn.wpseptembar.entities.*;

import java.sql.*;
import java.util.ArrayList;

public class UserRepository extends AbstractMySQLRepository implements IUserRepository {
    @Override
    public PaginatedRecord getUsers(int pageNumber) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Object> users = new ArrayList<Object>();
        int total = 0;

        try {
            connection = newConnection();

            String[] generatedColumns = {"id", "firstName", "lastName", "email", "admin", "active"};

            preparedStatement = connection.prepareStatement("SELECT count(*) FROM users");
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
                total = resultSet.getInt(1);

            preparedStatement = connection.prepareStatement("SELECT * FROM users LIMIT 10 OFFSET ?", generatedColumns);
            preparedStatement.setInt(1, (pageNumber - 1) * 10);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User user = new User();

                user.setId(resultSet.getInt(1));
                user.setFirstName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setEmail(resultSet.getString(4));
                user.setAdmin(resultSet.getBoolean(6));
                user.setActive(resultSet.getBoolean(7));

                users.add(user);
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

        return new PaginatedRecord(users, total, pageNumber, 10, (int) Math.ceil((double) total / 10));
    }

    @Override
    public User getUserById(int id) {
        return getUserBy(false, id);
    }

    @Override
    public User getUserByEmail(String email) {
        return getUserBy(true, email);
    }

    private User getUserBy(boolean email, Object value) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User resultUser = new User();

        try {
            connection = newConnection();

            String[] generatedColumns = {"id", "firstName", "lastName", "email", "admin", "active"};

            if (email) {
                preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE email = ?", generatedColumns);
                preparedStatement.setString(1, value.toString());
            } else {
                preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE id = ?", generatedColumns);
                preparedStatement.setInt(1, (int) value);
            }

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                resultUser.setId(resultSet.getInt(1));
                resultUser.setFirstName(resultSet.getString(2));
                resultUser.setLastName(resultSet.getString(3));
                resultUser.setEmail(resultSet.getString(4));
                resultUser.setAdmin(resultSet.getBoolean(6));
                resultUser.setActive(resultSet.getBoolean(7));
            } else {
                resultUser = null;
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

        return resultUser;
    }

    @Override
    public User registerUser(UserFormRegistration user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User resultUser = null;

        try {
            connection = newConnection();

            if (getUserBy(true, user.getEmail()) != null) {
                closeConnection(connection);
                return null;
            }

            String hashedPassword = BCrypt.withDefaults().hashToString(8, user.getPassword().toCharArray());

            preparedStatement = connection.prepareStatement("INSERT INTO users (firstName, lastName, email, password, admin, active) VALUES(?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, hashedPassword);
            preparedStatement.setBoolean(5, user.isAdmin());
            preparedStatement.setBoolean(6, user.isActive());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                resultUser = new User();
                resultUser.setId(resultSet.getInt(1));
                resultUser.setFirstName(user.getFirstName());
                resultUser.setLastName(user.getLastName());
                resultUser.setEmail(user.getEmail());
                resultUser.setAdmin(user.isAdmin());
                resultUser.setActive(user.isActive());
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

        return resultUser;
    }

    @Override
    public User editUser(UserFormEdit user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User resultUser = null;

        try {
            connection = newConnection();

            String[] generatedColumns = {"id"};

            if (getUserBy(false, user.getId()) == null) {
                closeConnection(connection);
                return resultUser;
            }

            if (user.getPassword() != null) {
                String hashedPassword = BCrypt.withDefaults().hashToString(8, user.getPassword().toCharArray());

                preparedStatement = connection.prepareStatement("UPDATE users SET firstName = ?, lastName = ?, email = ?, password = ?, admin = ?, active = ? WHERE id = ?", generatedColumns);

                preparedStatement.setString(1, user.getFirstName());
                preparedStatement.setString(2, user.getLastName());
                preparedStatement.setString(3, user.getEmail());
                preparedStatement.setString(4, hashedPassword);
                preparedStatement.setBoolean(5, user.isAdmin());
                preparedStatement.setBoolean(6, user.isActive());
                preparedStatement.setInt(7, user.getId());
            } else {
                preparedStatement = connection.prepareStatement("UPDATE users SET firstName = ?, lastName = ?, email = ?, admin = ?, active = ? WHERE id = ?", generatedColumns);

                preparedStatement.setString(1, user.getFirstName());
                preparedStatement.setString(2, user.getLastName());
                preparedStatement.setString(3, user.getEmail());
                preparedStatement.setBoolean(4, user.isAdmin());
                preparedStatement.setBoolean(5, user.isActive());
                preparedStatement.setInt(6, user.getId());
            }

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            resultUser = new User();

            resultUser.setId(user.getId());
            resultUser.setFirstName(user.getFirstName());
            resultUser.setLastName(user.getLastName());
            resultUser.setEmail(user.getEmail());
            resultUser.setAdmin(user.isAdmin());
            resultUser.setActive(user.isActive());
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

        return resultUser;
    }

    @Override
    public User login(LoginForm user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User resultUser = new User();

        try {
            connection = newConnection();

            String[] generatedColumns = {"id", "firstName", "lastName", "email", "password", "admin", "active"};

            preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE email = ?", generatedColumns);
            preparedStatement.setString(1, user.getEmail());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                resultUser.setId(resultSet.getInt(1));
                resultUser.setFirstName(resultSet.getString(2));
                resultUser.setLastName(resultSet.getString(3));
                resultUser.setEmail(resultSet.getString(4));
                String hash = resultSet.getString(5);
                resultUser.setAdmin(resultSet.getBoolean(6));
                resultUser.setActive(resultSet.getBoolean(7));

                BCrypt.Result result = BCrypt.verifyer().verify(user.getPassword().toCharArray(), hash);

                if (!result.verified) {
                    resultUser = null;
                }
            } else {
                resultUser = null;
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

        return resultUser;
    }
}
