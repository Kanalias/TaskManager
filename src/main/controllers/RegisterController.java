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

public class RegisterController {

    /*Поле логина*/
    public TextField loginText;
    /*Поле пароля*/
    public PasswordField passwordText;
    /*Поле подтверждения пароля*/
    public PasswordField confirmPasswordText;
    public Button registerButton;
    public Button backButton;


    /**
     * Функция регистрации и автоматического входа в приложение при успешном входе
     * проверяется корректность логина и введенных паролей
     * если все хорошо региструем пользователя и открываем основное окно
     *  */
    public void registerAndLogin(ActionEvent actionEvent) {
        Middleware middleware = new LoginMiddleware();
        middleware.linkWith(new PasswordMiddleware());
        String login = loginText.getText();
        String password = passwordText.getText();
        String confirmPassword = confirmPasswordText.getText();
        if (!middleware.check(login, password, confirmPassword)){
            boolean status = User.getInstance(login, password).register();
            if(status) {
                MyAlert.showAlert("Информация", "Пользователь успешно зарегестрирован", true);
                openScene("/main/views/main.fxml", loginText.getScene().getWindow());
            }
            else
                MyAlert.showAlert("Предупреждение", "Не удалось зарегестрироваться", false);
        } else {
            MyAlert.showAlert("Предупреждение", "Логин уже существует или пароли не совпадают", false);
        }
    }

    /**
     * Функция, функция которая инициализирует новое окно и открывает его
     * */
    private void openScene(String pathFxml, Window window){
        try {
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
     * Функция закрытия текущего окна приложения
     * */
    public void closeStage(ActionEvent actionEvent) {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }
}
