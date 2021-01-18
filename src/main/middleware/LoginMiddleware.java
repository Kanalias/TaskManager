package main.middleware;

import main.database.ContextDataBase;

public class LoginMiddleware extends Middleware{

    /* Класс проверяющий занятость логина */

    private ContextDataBase contextDataBase = ContextDataBase.getInstance();

    public LoginMiddleware() {

    }

    /**
     * Проверяет занят ли логин
     * */
    public boolean check(String login, String password, String confirmPassword) {
        if (login.equals(""))
            return false;

        if (!contextDataBase.checkLogin(login)) {
            return false;
        }

        return checkNext(login, password, confirmPassword);
    }
}
