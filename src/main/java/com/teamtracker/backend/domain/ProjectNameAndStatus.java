package com.teamtracker.backend.domain;

public class ProjectNameAndStatus {
    private String projectName;
    private String status;

    public ProjectNameAndStatus(String projectName, String status) {
        this.projectName = projectName;
        this.status = status;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
