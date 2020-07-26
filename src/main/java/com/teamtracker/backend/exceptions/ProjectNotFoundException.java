package com.teamtracker.backend.exceptions;

public class ProjectNotFoundException extends RuntimeException{

  public ProjectNotFoundException(String message) {
    super(message);
  }
}
