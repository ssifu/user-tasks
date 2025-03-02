package com.samiulsifat.task_management.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@DynamoDBTable(tableName = "Tasks")
@DynamoDBDocument
public class Task {
    @DynamoDBHashKey(attributeName = "task_id")
    private String taskId;
    @DynamoDBAttribute
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "AssignedToIndex", attributeName = "assigned_to")
    private String assignedTo;
    @DynamoDBAttribute
    private String title;
    @DynamoDBAttribute
    private String description;
    @DynamoDBAttribute
    private String status;
    @DynamoDBAttribute
    private String createdAt;
    @DynamoDBAttribute
    private String dueDate;
    @DynamoDBAttribute
    private String assignedBy;

    public Task() {
    }


    public Task(
            String taskId,
            String assignedTo,
            String title,
            String description,
            String status,
            String createdAt,
            String dueDate,
            String assignedBy
    ) {
        this.taskId = taskId;
        this.assignedTo = assignedTo;
        this.title = title;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.dueDate = dueDate;
        this.assignedBy = assignedBy;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(String assignedBy) {
        this.assignedBy = assignedBy;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId='" + taskId + '\'' +
                ", assignedTo='" + assignedTo + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", assignedBy='" + assignedBy + '\'' +
                '}';
    }
}
