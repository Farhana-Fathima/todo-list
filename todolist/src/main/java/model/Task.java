package model;

import java.time.LocalDate;

public class Task {
	
	private int id;
    private String title;
    private String description;
    private LocalDate taskDate;
    private boolean completed;

    // Constructors
    public Task() {}

    public Task(String title, String description, LocalDate taskDate) {
        this.title = title;
        this.description = description;
        this.taskDate = taskDate;
        this.completed = false;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getTaskDate() { return taskDate; }
    public void setTaskDate(LocalDate taskDate) { this.taskDate = taskDate; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }


}
 