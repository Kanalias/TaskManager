package main.controllers;

import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.model.Task;
import main.model.User;
import main.model.UserTasks;


public class TaskController {

    public TextField nameTaskText;
    public TextArea descriptionTaskText;
    public Button saveTaskButton;
    public CheckBox statusCheckBox;
    public Button deleteTask;
    /* Тип операции создание, изменение задачи*/
    private String type;
    /*сама задача */
    private Task task;

    /**
     * Обработчик создания задачи
     * добавляет задачу в списк задач и базу данных
     * */
    private EventHandler<MouseEvent> saveTaskHandler() {
        return mouseEvent -> {
            String nameTask = nameTaskText.getText();
            String descriptionTask = descriptionTaskText.getText();

            if (nameTask.isEmpty()) {
                MyAlert.showAlert("Предупреждение", "Не введено имя задачи", false);
            } else {
                // сохраняем задачу в базу данных
                Task task = new Task(nameTask, descriptionTask, 0, User.getInstance().getId());

                boolean status = task.addTask();
                if (!status) {
                    MyAlert.showAlert("Предупреждение", "Не удалось добавить задачу", false);
                } else {
                    UserTasks.getInstance().addTask(task);
                    this.close();
                }

            }
        };
    }

    /**
     * Обработчик изменения задачи
     * изменяет задачу из списка задач и базы данных
     * */
    private EventHandler<MouseEvent> editTaskHandler() {
        return mouseEvent -> {
            String nameTask = nameTaskText.getText();
            String descriptionTask = descriptionTaskText.getText();
            boolean isDone = statusCheckBox.isSelected();

            if (nameTask.isEmpty()) {
                MyAlert.showAlert("Предупреждение", "Не введено имя задачи", false);
            } else {
                // сохраняем задачу в базу данных
                task.setName(nameTask);
                task.setDescriptionTask(descriptionTask);
                task.setReadinessStatus(isDone ? 1 : 0);

                boolean status = task.updateTask();
                if (!status) {
                    MyAlert.showAlert("Предупреждение", "Не удалось добавить задачу", false);
                } else {
                    UserTasks.getInstance().update(task);
                    this.close();
                }

            }
        };
    }

    /**
     * Обработчик удаления задачи
     * удаляет задачу из списка задач и базы данных
     * */
    private EventHandler<MouseEvent> deleteTaskHandler() {
        return mouseEvent -> {
            final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Предупреждение");
            alert.setContentText("Вы действительно хотите удалить запись?");
            alert.initOwner(deleteTask.getScene().getWindow());
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.CANCEL) {

                }

                if (response == ButtonType.OK) {
                    boolean status = task.deleteTask();
                    if (!status) {
                        MyAlert.showAlert("Предупреждение", "Не удалось добавить задачу", false);
                    } else {
                        UserTasks.getInstance().deleteTask(task);
                        this.close();
                    }
                }
            });
        };
    }

    /**
     * Задает отображение в зависимости от типа
     * При типе edit появляется чек бок с состоянием готовности задачи и кнопка удалит задание
     * */
    public void setType(String type, Task task){
        this.type = type;
        this.task = task;

        if (type.equals("edit")){
            saveTaskButton.setText("Сохранить изменения");
            saveTaskButton.addEventHandler(MouseEvent.MOUSE_CLICKED, editTaskHandler());
            nameTaskText.setText(task.getName());
            descriptionTaskText.setText(task.getDescription());
            statusCheckBox.setVisible(true);
            deleteTask.setVisible(true);
            deleteTask.addEventHandler(MouseEvent.MOUSE_CLICKED, deleteTaskHandler());
            statusCheckBox.setSelected(task.getReadinessStatus() == 1);

        }

        if (type.equals("create")){
            saveTaskButton.setText("Создать");
            saveTaskButton.addEventHandler(MouseEvent.MOUSE_CLICKED, saveTaskHandler());
            statusCheckBox.setVisible(false);
            deleteTask.setVisible(false);
        }
    }


    /**
     * Закрывает текущее окно
     * */
    private void close(){
        Stage stage = (Stage) saveTaskButton.getScene().getWindow();
        stage.close();
    }

    public void initialize() {

    }
}
