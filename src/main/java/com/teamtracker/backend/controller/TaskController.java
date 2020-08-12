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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    List<String> taskNames = taskService.createOrUpdateTaskByIncomingTask(projectTask);
    return new ResponseEntity<List<String>>(taskNames, HttpStatus.OK);
  }

  @PostMapping("/tasksByProject")
  public ResponseEntity<?> getTasksByProject(@Valid @RequestBody Project project, BindingResult result){
     ResponseEntity<?> errMap = mapValidationErrorService.MapValidationService(result);
     if (errMap != null) {
       return errMap;
     }
    Iterable<ProjectTask> tasksByProject = taskService.getTasksByProject(project);
    return new ResponseEntity<Iterable<ProjectTask>>(tasksByProject, HttpStatus.OK);
  }

  @PostMapping("/tasksTodoByProject")
  public ResponseEntity<?> getTasksTodoByProject(@Valid @RequestBody Project project, BindingResult result){
     ResponseEntity<?> errMap = mapValidationErrorService.MapValidationService(result);
     if (errMap != null) {
       return errMap;
     }
    List<String> taskNames = taskService.getTasksTodoByProject(project);
    return new ResponseEntity<List<String>>(taskNames, HttpStatus.OK);
  }

  @PostMapping("/tasksDoingByProject")
  public ResponseEntity<?> getTasksDoingByProject(@Valid @RequestBody Project project, BindingResult result){
    ResponseEntity<?> errMap = mapValidationErrorService.MapValidationService(result);
    if (errMap != null) {
      return errMap;
    }
    List<String> taskNames = taskService.getTasksDoingByProject(project);
    return new ResponseEntity<List<String>>(taskNames, HttpStatus.OK);
  }

  @PostMapping("/tasksDoneByProject")
  public ResponseEntity<?> getTasksDoneByProject(@Valid @RequestBody Project project, BindingResult result){
    ResponseEntity<?> errMap = mapValidationErrorService.MapValidationService(result);
    if (errMap != null) {
      return errMap;
    }
    List<String> taskNames = taskService.getTasksDoneByProject(project);
    return new ResponseEntity<List<String>>(taskNames, HttpStatus.OK);
  }

  @PostMapping("/updateTask")
  public ResponseEntity<?> updateTask(@Valid @RequestBody ProjectTask projectTask,
                                         BindingResult result) {
    ResponseEntity<?> errMap = mapValidationErrorService.MapValidationService(result);
    if (errMap != null) {
      return errMap;
    }
    List<String> taskNames = taskService.createOrUpdateTaskByIncomingTask(projectTask);
    return new ResponseEntity<List<String>>(taskNames, HttpStatus.OK);
  }

  @GetMapping("/search")
  public ResponseEntity<?> getAllProjectTaskNamesBySearch(@Valid @RequestBody Map<String, String> jsonMap, BindingResult result) {
    ResponseEntity<?> errMap = mapValidationErrorService.MapValidationService(result);
    if (errMap != null) {
      return errMap;
    }
    String ownerName = jsonMap.get(("ownerName"));
    String projectName = jsonMap.get(("projectName"));
    String searchedString = jsonMap.get("searchedString");
    Iterable<ProjectTask> foundTasks = taskService.searchByString(ownerName, projectName, searchedString);
    return new ResponseEntity<Iterable<ProjectTask>>(foundTasks, HttpStatus.OK);
  }

}
