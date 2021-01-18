package main.model;

import main.database.ContextDataBase;

public class User {

    /* Класс пользователя, который содержит данные о пользователе и делегирует команды из базы данных в себе*/

    /*Является классом одиночкой*/

    ContextDataBase contextDataBase = ContextDataBase.getInstance();

    private static User instance;

    private String login;
    private String password;
    private int id;

    private User(String login, String password){
        this.login = login;
        this.password = password;
        this.id = getUserIdDB();
    }

    private User(){
    }

    public static User getInstance(String login, String password){
        if (instance == null) {
            instance = new User(login, password);
        }
        return instance;
    }

    public static User getInstance(){
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    public int getUserIdDB(){
        return contextDataBase.getUserData(login, password);
    }

    public String getLogin() {
        return login;
    }

    public int getId(){
        return id;
    }

    public void logout(){
        if (instance != null){
            instance = null;
        }
    }

    public boolean login(String login, String password){
        return contextDataBase.loginUser(login, password);
    }

    public boolean register() {
        return contextDataBase.registerUser(login, password);
    }
}
