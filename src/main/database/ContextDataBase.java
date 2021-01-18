package main.database;

import main.model.Task;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ContextDataBase {

    /* Класс управления базой данных,
    является классом одиночкой
    База данныйх используется SQLite
     */

    private static ContextDataBase instance;
    private static Connection conn;

    private ContextDataBase(){
        try {
            conn = getConnection();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static ContextDataBase getInstance(){
        if (instance == null) {
            instance = new ContextDataBase();
        }
        return instance;
    }

    /**
     * Получает подключение к базе данных
     * */
    public Connection getConnection() throws SQLException, IOException, ClassNotFoundException {

        Class.forName("org.sqlite.JDBC");

        Properties props = new Properties();

        try(InputStream in = Files.newInputStream(Paths.get("src/main/database.properties"))){
            props.load(in);
        }

        String url = props.getProperty("url");

        return DriverManager.getConnection(url);
    }


    /**
     * Проверяет существует ли данный логин в бд
     * */
    public boolean checkLogin(String login) {
        int id = -1;
        try (Statement statement = conn.createStatement()) {

            ResultSet resultSet = statement.executeQuery(
                    "SELECT id, login, password FROM users WHERE login=" + login
            );

            id = resultSet.getInt("id");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id != -1;
    }

    /**
     * Создает пользователя с логином и паролем в бд
     */
    public boolean registerUser(String login, String password){
        boolean status = false;
        try (PreparedStatement statement = conn.prepareStatement(
                "INSERT INTO users(`login`, `password`) VALUES(?, ?)"))
        {
            statement.setObject(1, login);
            statement.setObject(2, password);

            statement.execute();
            status = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return status;
    }

    /**
     * Проверяет есть ли пользователь с данным логином и паролем в бд
     */
    public boolean loginUser(String login, String password){

        int id = getUserData(login, password);
        return id != -1;
    }

    /**
     * Проверяет есть ли пользователь с данным логином и паролем в бд
     */
    public int getUserData(String login, String password){
        int id = -1;
        try (Statement statement = conn.createStatement()) {

            ResultSet resultSet = statement.executeQuery(
                    "SELECT id, login, password FROM users WHERE login=" + login + " and password=" + password
            );


            id = resultSet.getInt("id");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    /**
     * Добавляет задачу в бд
     */
    public boolean addTask(String name, String description, int readineesStatus, int userId){
        boolean status = false;
        try (PreparedStatement statement = conn.prepareStatement(
                "INSERT INTO tasks(`name`, `description`, `readiness_status`, `user_id`) VALUES(?, ?, ?, ?)"))
        {
            statement.setObject(1, name);
            statement.setObject(2, description);
            statement.setObject(3, readineesStatus);
            statement.setObject(4, userId);
            statement.execute();
            status = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    /**
     * Удаляет задачу из бд
     */
    public boolean deleteTask(int taskId){
        boolean status = false;
        try (PreparedStatement statement = conn.prepareStatement(
                "DELETE FROM tasks WHERE id = ?"))
        {
            statement.setObject(1, taskId);
            statement.execute();
            status = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    /**
     * Обновляет задачу в бд
     */
    public boolean updateTask(String name, String description, int readineesStatus, int id){
        boolean status = false;
        try (PreparedStatement statement = conn.prepareStatement(
                "UPDATE tasks SET name = ?, description = ?, readiness_status = ? WHERE id = ?"))
        {
            statement.setObject(1, name);
            statement.setObject(2, description);
            statement.setObject(3, readineesStatus);
            statement.setObject(4, id);
            statement.executeUpdate();
            status = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    /**
     * Получает задачу по айди из бд
     */
    public Task getTask(int taskId){
        try (Statement statement = conn.createStatement()) {

            ResultSet resultSet = statement.executeQuery(
                    "SELECT id, name , description, readiness_status, user_id FROM tasks WHERE id=" + taskId
            );


            return new Task(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getInt("readiness_status"),
                    resultSet.getInt("user_id")
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Получает список всех задач для конкретного пользователя из бд
     */
    public List<Task> getTasks(int userId){
        try (Statement statement = conn.createStatement()) {
            List<Task> tasks = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery("SELECT  id, name , description, readiness_status, user_id FROM tasks WHERE user_id=" + userId);

            while (resultSet.next()) {
                tasks.add(new Task(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getInt("readiness_status"),
                        resultSet.getInt("user_id")
                ));
            }

            return tasks;

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return null;
    }
}
