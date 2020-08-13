package com.teamtracker.backend.controller;

import com.sun.deploy.panel.ITreeNode;
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
import org.springframework.web.bind.annotation.*;

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
  @DeleteMapping("/byTask")
  public ResponseEntity<?> deleteTaskByExistingTask(
          @Valid @RequestBody ProjectTask inputTask, BindingResult result
  ) {
    ResponseEntity<?> errMap = mapValidationErrorService.MapValidationService(result);
    if (errMap != null) {
      return errMap;
    }
    List<String> taskNames = taskService.deleteByExistingTask(inputTask);

    return new ResponseEntity<List<String>>(taskNames, HttpStatus.OK);
  }
  @PostMapping("/updateTask")
  public ResponseEntity<?> updateTask(@Valid @RequestBody Map<String, String> jsonMap, BindingResult result) {
    ResponseEntity<?> errMap = mapValidationErrorService.MapValidationService(result);
    if (errMap != null) {
      return errMap;
    }
    String ownerName = jsonMap.get(("ownerName"));
    String projectName = jsonMap.get("projectName");
    String taskName = jsonMap.get("taskName");
    String taskDescription = jsonMap.get("taskDescription");
    String status = jsonMap.get("status");
    String originalTaskName = jsonMap.get("originalTaskName");
    List<String> updatedTaskNames = taskService.updateTaskByOriginalTaskName(projectName, ownerName, taskName, taskDescription, status, originalTaskName);
    return new ResponseEntity<List<String>>(updatedTaskNames, HttpStatus.OK);
  }

  @PostMapping("/search")
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

  @PostMapping("/moveTaskStatus")
  public ResponseEntity<?> moveTaskStatus(@Valid @RequestBody Map<String, String> jsonMap, BindingResult result) {
    ResponseEntity<?> errMap = mapValidationErrorService.MapValidationService(result);
    if (errMap != null) {
      return errMap;
    }
    String ownerName = jsonMap.get(("ownerName"));
    String projectName = jsonMap.get(("projectName"));
    String taskName = jsonMap.get("taskName");
    String newStatus = jsonMap.get("newStatus");

    List<List<ProjectTask>> oldTasksAndNewTasks = taskService.moveTaskStatus(projectName, ownerName, taskName, newStatus);
//    Iterable<Iterable<ProjectTask>> res = oldTasksAndNewTasks; // 不合法
    return new ResponseEntity<List<List<ProjectTask>>>(oldTasksAndNewTasks, HttpStatus.OK);
  }

}
