package main.model;

import main.database.ContextDataBase;

public class Task {

    /* Класс, который содержит задачу и делегирует вызовы у базы данных в себе */

    ContextDataBase contextDataBase = ContextDataBase.getInstance();

    private int id;
    private String name;
    private String description;
    private int readinessStatus;
    private int userId;


    public Task(int id, String name, String description, int readinessStatus, int userId){
        this.id = id;
        this.name = name;
        this.description = description;
        this.readinessStatus = readinessStatus;
        this.userId = userId;
    }

    public Task(String name, String description, int readineesStatus, int userId) {
        this.name = name;
        this.description = description;
        this.readinessStatus = readineesStatus;
        this.userId = userId;
    }

    public boolean addTask(){
        return contextDataBase.addTask(name, description, 0, userId);
    }

    public boolean updateTask(){
        return contextDataBase.updateTask(name, description, readinessStatus, id);
    }

    public boolean deleteTask(){
        return contextDataBase.deleteTask(id);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getReadinessStatus() {
        return readinessStatus;
    }

    public int getUserId() {
        return userId;
    }

    public void setName(String nameTask) {
        this.name = nameTask;
    }

    public void setDescriptionTask(String descriptionTask) {
        this.description = descriptionTask;
    }

    public void setReadinessStatus(int i) {
        this.readinessStatus = i;
    }
}
