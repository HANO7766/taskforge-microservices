package com.taskforge.project.event;

import java.io.Serializable;
import java.util.UUID;

public class TaskEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID taskId;
    private UUID assigneeId;
    private String taskTitle;
    private String eventType;

    public TaskEvent() {}

    private TaskEvent(Builder b) {
        this.taskId = b.taskId;
        this.assigneeId = b.assigneeId;
        this.taskTitle = b.taskTitle;
        this.eventType = b.eventType;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private UUID taskId, assigneeId;
        private String taskTitle, eventType;

        public Builder taskId(UUID id) { this.taskId = id; return this; }
        public Builder assigneeId(UUID id) { this.assigneeId = id; return this; }
        public Builder taskTitle(String t) { this.taskTitle = t; return this; }
        public Builder eventType(String e) { this.eventType = e; return this; }
        public TaskEvent build() { return new TaskEvent(this); }
    }

    public UUID getTaskId() { return taskId; }
    public void setTaskId(UUID taskId) { this.taskId = taskId; }
    public UUID getAssigneeId() { return assigneeId; }
    public void setAssigneeId(UUID assigneeId) { this.assigneeId = assigneeId; }
    public String getTaskTitle() { return taskTitle; }
    public void setTaskTitle(String taskTitle) { this.taskTitle = taskTitle; }
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
}
