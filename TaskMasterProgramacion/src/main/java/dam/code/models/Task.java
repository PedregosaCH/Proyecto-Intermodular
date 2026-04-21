package dam.code.models;

import java.time.LocalDate;

public class Task {
    private int id;
    private String taskName;
    private String taskDescription;
    private LocalDate creationDate;
    private LocalDate expirationDate;

    public Task(String taskName, String taskDescription, LocalDate creationDate, LocalDate expirationDate) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
    }

    public Task(int id, String taskName, String taskDescription, LocalDate creationDate, LocalDate expirationDate) {
        this.id = id;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
}
