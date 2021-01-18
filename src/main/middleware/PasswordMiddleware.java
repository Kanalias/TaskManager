package main.middleware;

import main.database.ContextDataBase;

public class PasswordMiddleware extends Middleware{

    /* Класс проверяющий корректность пароля */

    private ContextDataBase contextDataBase = ContextDataBase.getInstance();

    public PasswordMiddleware() {

    }

    public boolean check(String login, String password, String confirmPassword) {

        if (password.equals(""))
            return false;

        if (!confirmPassword.equals(""))
             if (!password.equals(confirmPassword))
                 return false;

        if (!contextDataBase.loginUser(login, password)) {
            return false;
        }

        return checkNext(login, password, confirmPassword);
    }

}
