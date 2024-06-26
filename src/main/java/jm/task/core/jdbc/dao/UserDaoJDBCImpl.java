package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final Connection connection = Util.connection();

    public UserDaoJDBCImpl() throws SQLException {

    }

    /**
     * метод Создание таблицы User(ов)
     * */
   public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (id BIGINT(19) NOT NULL AUTO_INCREMENT, " +
                    "name VARCHAR(40) NOT NULL, lastName VARCHAR(40) NOT NULL, " +
                    "age TINYINT(3) NOT NULL, PRIMARY KEY (id));");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * метод Удаление таблицы
     * */
    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {

            statement.executeUpdate("DROP TABLE IF EXISTS users;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * метод Добавление 4 User(ов) в таблицу
     * */
    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO users (name, lastname, age) " +
                "VALUES(?, ?, ?)")) {

            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * метод удаления Юзера по id
     * */
    public void removeUserById(long id) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {
            statement.setLong(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * метод Получение всех User из базы и вывод в консоль (должен быть переопределен toString в классе User)
     * */
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

            while (resultSet.next()) {
                User users = new User();

                users.setId(resultSet.getLong("id"));
                users.setName(resultSet.getString("name"));
                users.setLastName(resultSet.getString("lastName"));
                users.setAge(resultSet.getByte("age"));

                userList.add(users);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    /**
     * метод Очистка таблицы User(ов)
     * */
    public void cleanUsersTable() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("TRUNCATE TABLE users;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
