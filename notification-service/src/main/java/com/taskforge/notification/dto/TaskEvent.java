package com.taskforge.notification.dto;

import java.io.Serializable;
import java.util.UUID;

public class TaskEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID taskId;
    private UUID assigneeId;
    private String taskTitle;
    private String eventType;

    public TaskEvent() {}

    public TaskEvent(UUID taskId, UUID assigneeId, String taskTitle, String eventType) {
        this.taskId = taskId;
        this.assigneeId = assigneeId;
        this.taskTitle = taskTitle;
        this.eventType = eventType;
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
