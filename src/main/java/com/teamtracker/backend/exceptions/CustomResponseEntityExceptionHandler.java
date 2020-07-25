package com.teamtracker.backend.exceptions;

import javax.jws.WebResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
  @ExceptionHandler
  public final ResponseEntity<Object> handleProjectNameException(ProjectNameException exception,
      WebResult result){
    ProjectNameExceptionResponse exceptionResponse = new ProjectNameExceptionResponse(exception.getMessage());
    return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
  }
}
