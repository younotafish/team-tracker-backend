package com.teamtracker.backend.controller;

import com.teamtracker.backend.domain.Project;
import com.teamtracker.backend.domain.ProjectTask;
import com.teamtracker.backend.service.MapValidationErrorService;
import com.teamtracker.backend.service.ProjectService;
import com.teamtracker.backend.service.TaskService;
import com.teamtracker.backend.service.UserService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/task")
public class TaskController {

  @Autowired
  ProjectService projectService;
  @Autowired
  UserService userService;
  @Autowired
  TaskService taskService;
  @Autowired
  MapValidationErrorService mapValidationErrorService;

  @PostMapping("")
  public ResponseEntity<?> createNewTask(@Valid @RequestBody ProjectTask projectTask,
      BindingResult result) {
    ResponseEntity<?> errMap = mapValidationErrorService.MapValidationService(result);
    if (errMap != null) {
      return errMap;
    }
    ProjectTask newProjectTask = taskService.createOrUpdateTaskByIncomingTask(projectTask);
    return new ResponseEntity<ProjectTask>(newProjectTask, HttpStatus.OK);
  }
  @PostMapping("/tasks")
  public Iterable<ProjectTask> getTaskByProject(@Valid @RequestBody Project project, BindingResult result){
    // ResponseEntity<?> errMap = mapValidationErrorService.MapValidationService(result);
    // if (errMap != null) {
    //   return errMap;
    // }
    return taskService.getTasksByProject(project);
  }
}
