package com.elias.todo.model;

public class Task {

    private int id;
    private String description;
    private String time;
    private String level;
    private String folder;
    private String status;

    public Task() {
    }
    public Task(String description, String time, String level, String folder, String status) {
        this.description = description;
        this.time = time;
        this.level = level;
        this.folder = folder;
        this.status = status;
    }
    public Task(int id, String description, String time, String level, String folder, String status) {
        this.id = id;
        this.description = description;
        this.time = time;
        this.level = level;
        this.folder = folder;
        this.status = status;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getLevel() {
        return level;
    }
    public void setLevel(String level) {
        this.level = level;
    }
    public String getFolder() {
        return folder;
    }
    public void setFolder(String folder) {
        this.folder = folder;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

}
