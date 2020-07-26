package com.teamtracker.backend.controller;

import com.teamtracker.backend.domain.Project;
import com.teamtracker.backend.domain.User;
import com.teamtracker.backend.service.MapValidationErrorService;
import com.teamtracker.backend.service.ProjectService;
import com.teamtracker.backend.service.UserService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

  @Autowired
  ProjectService projectService;
  @Autowired
  MapValidationErrorService mapValidationErrorService;
  @Autowired
  UserService userService;

  @PostMapping("")
  public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project,
      BindingResult result) {
    ResponseEntity<?> errMap = mapValidationErrorService.MapValidationService(result);
    if (errMap != null) {
      return errMap;
    }
    Project project1 = projectService.saveOrUpdateProject(project);
    return new ResponseEntity<Project>(project1, HttpStatus.OK);
  }

  @GetMapping("/{projectName}")
  public ResponseEntity<?> getProjectByName(@PathVariable String projectName) {
    Project project = projectService.findByProjectName(projectName);
    return new ResponseEntity<Project>(project, HttpStatus.OK);
  }

  @GetMapping("/all")
  public Iterable<Project> getAllProject() {
    return projectService.findAllProject();
  }

  @DeleteMapping("/{projectName}")
  public void deleteProjectByName(@PathVariable String projectName) {
    projectService.deleteProjectByName(projectName);
  }

  @GetMapping("/own/{ownerName}")
  public Iterable<Project> getAllProjectByOwnerName(@PathVariable String ownerName) {
    User theUser = userService.findByUserName(ownerName);
    return projectService.findAllByUser(theUser);
  }

  @GetMapping("/par/{parName}")
  public Iterable<Project> getAllProjectByParName(@PathVariable String parName) {
    User theUser = userService.findByUserName(parName);
    return projectService.findAllByPar(theUser);
  }

  @PostMapping("/engage")
  public ResponseEntity<?> engageProject(@RequestBody Map<String, String> jsonMap) {
    String participantName = jsonMap.get("participantName");
    String ownerName = jsonMap.get(("ownerName"));
    String projectName = jsonMap.get("projectName");

    Project project1 = projectService.addParticipant(participantName, ownerName, projectName);

    return new ResponseEntity<Project>(project1, HttpStatus.OK);
  }

  @GetMapping("/collaborators")
  public Iterable<?> getAllCollaborators(@RequestBody Project project) {
    Iterable<User> collaborators = userService.getCollaboratorsByProject(project);
    // projectPar 可能需要
    //    for (User user : collaborators
//    ) {
//      user.setProjectParticipated(null);
//    }
    return userService.getCollaboratorsByProject(project);
  }


}
