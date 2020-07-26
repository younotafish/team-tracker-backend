package com.teamtracker.backend.exceptions;

public class ProjectNameExceptionResponse {

  private String projectName;

  public String getProjectName() {
    return projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public ProjectNameExceptionResponse(String projectName) {
    this.projectName = projectName;
  }
}
