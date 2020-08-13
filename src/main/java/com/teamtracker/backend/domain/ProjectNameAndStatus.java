package com.teamtracker.backend.domain;

public class ProjectNameAndStatus {
    private String projectName;
    private String status;
    private String projectDescription;

    public ProjectNameAndStatus(String projectName, String status, String projectDescription) {
        this.projectName = projectName;
        this.status = status;
        this.projectDescription = projectDescription;
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

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }
}
