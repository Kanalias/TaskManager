package main.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import main.middleware.LoginMiddleware;
import main.middleware.Middleware;
import main.middleware.PasswordMiddleware;
import main.model.User;

import java.io.IOException;


public class LoginController {

    /* Контроллер который отвечает за вью логина */

    /* Текстовое поле с логином */
    public TextField loginText;
    /* Текстовое поле с паролем */
    public PasswordField passwordText;
    /* Кнопка логина */
    public Button loginButton;
    /* Кнопка регистрации */
    public Button registerButton;

    /**
     * Функция входа в основное окно
     * происходит проверка логина и пароля
     * Если все хорошо, то авторизуем пользователя и открываем основное окно
     * */
    public void login(ActionEvent actionEvent) {
        Middleware middleware = new LoginMiddleware();
        middleware.linkWith(new PasswordMiddleware());
        String login = loginText.getText();
        String password = passwordText.getText();
        if (middleware.check(login, password, "")){
            User.getInstance(login, password);
            openScene("/main/views/main.fxml", null);
        } else {
            MyAlert.showAlert("Предупреждение", "Неверный логин или пароль", false);
        }
    }

    /**
     * Функция, функция которая инициализирует новое окно и открывает его
     * */
    private void openScene(String pathFxml, Window window){
        try{
            Parent root = FXMLLoader.load(getClass().getResource(pathFxml));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(window);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Функция открытия окна регистрации
     * */
    public void register(ActionEvent actionEvent) {
        openScene("/main/views/register.fxml", loginText.getScene().getWindow());
    }
}
