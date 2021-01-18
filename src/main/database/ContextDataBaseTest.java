package main.database;

import main.model.Task;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ContextDataBaseTest {

    ContextDataBase contextDataBase = ContextDataBase.getInstance();

    @Test
    public void getInstance(){
        Assert.assertSame(contextDataBase, ContextDataBase.getInstance());
    }

    @Test
    public void checkLogin() {

        // Если логин есть то тест пройдет иначе нет

        String login = "123";
        // String login = "321";

        boolean expected = contextDataBase.checkLogin(login);
        boolean actual = true;

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void registerUser() {

        // Если удалось зарегестироваться то тест пройдет

        String login = "123";
        String password = "123";
        // String login = "321";

        boolean expected = contextDataBase.registerUser(login, password);
        boolean actual = true;

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void loginUser() {

        // Если данный логин и пароль есть в бд, то тест пройдет

        String login = "123";
        String password = "123";

        boolean expected = contextDataBase.loginUser(login, password);
        boolean actual = true;

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getUserData() {

        // Если айди совпадают то тест пройдет

        String login = "123";
        String password = "123";

        int expected = contextDataBase.getUserData(login, password);
        int actual = 1;

        Assert.assertEquals(expected, actual);

    }

    @Test
    public void addTask() {

        boolean expected = contextDataBase.addTask(null, "", 0, 1);
        boolean actual = true;

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void deleteTask() {
        boolean expected = contextDataBase.deleteTask(8);
        boolean actual = true;

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void updateTask() {
        boolean expected = contextDataBase.updateTask("test", "2424", 1, 7);
        boolean actual = true;

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getTask() {
        Task task = new Task(1,"тест", "123", 0,1 );

        Task expected = ContextDataBase.getInstance().getTask(1);

        Assert.assertEquals(expected, task);
    }

    @Test
    public void getTasks() {
        Task task = new Task(1,"тест", "123", 0,1 );
        Task task1 = new Task(2,"тест1", "1234",0, 1);
        Task task2 = new Task(3, "тест2", "1234567", 0, 1);
        Task task3 = new Task(4, "тест3", "13536346", 0, 1);
        Task task4 = new Task(5, "тест4", "1234214", 0, 1);

        List<Task> expected = ContextDataBase.getInstance().getTasks(1);

        List<Task>  actual = new ArrayList<>();
        actual.add(task);
        actual.add(task1);
        actual.add(task2);
        actual.add(task3);
        actual.add(task4);

        Assert.assertEquals(expected, actual);
    }
}