package com.teamtracker.backend.exceptions;

public class UserNameExceptionResponse {

  public String userName;

  public UserNameExceptionResponse(String userName) {
    this.userName = userName;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }
}
