package main.controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.ParallelCamera;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import main.database.ContextDataBase;
import main.model.Task;
import main.model.User;
import main.model.UserTasks;

import java.io.IOException;
import java.util.List;

public class MainController {

    /* Лейб в который выводится текущий логин пользователя*/
    public Label loginLabel;
    public Button addTaskButton;
    public Button logoutButton;
    /* Основной компонент где хранятся задачи */
    public VBox vBoxScrollPane;

    /**
     * Открывает окно для создания задачи, после чего обновляет список задач
     * */
    public void addTask(ActionEvent actionEvent) {
        openScene("/main/views/task.fxml", addTaskButton.getScene().getWindow(), "create", null);
        printTask(UserTasks.getInstance().getTasks());
    }


    /**
     * Формирует из списка задач компоненты куда вставляет данные о задачах,
     * после вставляет компоненты в основной компонент, где отображаются все задачи пользвателя
     * */
    private void printTask(List<Task> tasks){
        vBoxScrollPane.getChildren().clear();
        if (tasks == null || tasks.isEmpty()){

            Label label = new Label("Вы не добавили ни одной задачи");
            vBoxScrollPane.getChildren().add(label);

        } else {
            for (Task task : tasks) {
                HBox hBox = new HBox();
                hBox.setPadding(new Insets(8, 8, 8, 8));
                Label id = new Label(Integer.toString(task.getId()));
                id.setVisible(false);
                Label label = new Label(task.getName());
                if (task.getReadinessStatus() == 0)
                    label.setDisable(false);
                else
                    label.setDisable(true);

                label.setPadding(new Insets(0, 8, 0, 0));

                Button button = new Button("Подробнее");
                button.addEventHandler(MouseEvent.MOUSE_CLICKED, eventEventHandler(button));
                hBox.getChildren().addAll(id, label, button);
                vBoxScrollPane.getChildren().add(hBox);
            }
        }
    }

    /**
     * Обработчик нажатия кнопки "Подробнее"
     * для изменения имени, описания, статуса задачи.
     * */
    private EventHandler<MouseEvent> eventEventHandler(Button button){
        return mouseEvent -> {

            HBox parent = (HBox) button.getParent();
            Label label = (Label) parent.getChildren().get(0);
            int taskId = Integer.parseInt(label.getText());
            List<Task> tasks = UserTasks.getInstance().getTasks();
            Task task = null;
            for (Task task1: tasks) {
                if (task1.getId() == taskId){
                    task = task1;
                    break;
                }
            }

            openScene("/main/views/task.fxml", button.getScene().getWindow(), "edit", task);
            printTask(UserTasks.getInstance().getTasks());
        };
    }

    /**
     * Функция инициализации контроллера,
     * выполняются базовые операции как вставка логина
     * и загрузка задач
     * */
    public void initialize() {
        loginLabel.setText(User.getInstance().getLogin());

        // Загрузить задачи.
        List<Task> tasks = ContextDataBase.getInstance().getTasks(User.getInstance().getId());
        UserTasks.getInstance(tasks);
        printTask(UserTasks.getInstance(tasks).getTasks());

    }


    /**
     * Выходит из текущего аккаунта на окно авторизации
     * */
    public void logout(ActionEvent actionEvent) {
        User.getInstance().logout();

        Stage stage = (Stage) addTaskButton.getScene().getWindow();
        stage.close();
    }


    private void openScene(String pathFxml, Window window, String typeWindow, Task task){
        try {
            FXMLLoader root = new FXMLLoader(getClass().getResource(pathFxml));
            Stage stage = new Stage();
            stage.setScene(new Scene(root.load()));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(window);
            TaskController controller = root.getController();
            controller.setType(typeWindow, task);
            stage.setResizable(false);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
