package main.controllers;

import javafx.scene.control.Alert;

public class MyAlert {

    /** Функция которая показывает всплывающее окно с переданными параметрами */
    public static void showAlert(String title, String content, boolean information) {
        Alert.AlertType alertType;
        if (information)
            alertType = Alert.AlertType.INFORMATION;
        else
            alertType = Alert.AlertType.WARNING;

        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
