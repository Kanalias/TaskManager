package main.model;

import java.util.List;

public class UserTasks {

    /* Класс содержащий список задач и функции для управления списком */

    /*Является классом одиночкой*/

    private static UserTasks instance;

    private List<Task> tasks;

    private UserTasks(List<Task> tasks){
        this.tasks = tasks;
    }

    private UserTasks(){

    }

    public static UserTasks getInstance(List<Task> tasks){
        if (instance == null) {
            instance = new UserTasks(tasks);
        }
        return instance;
    }

    public static UserTasks getInstance(){
        if (instance == null) {
            instance = new UserTasks();
        }
        return instance;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task){
        tasks.add(task);
    }

    public void deleteTask(Task task){
        tasks.remove(task);
    }

    public void update(Task task){
        //tasks.(task);
        for (Task task1: tasks) {
            if (task1.getId() == task.getId()){
                int index = tasks.indexOf(task1);
                tasks.set(index, task);
            }
        }
    }
}
