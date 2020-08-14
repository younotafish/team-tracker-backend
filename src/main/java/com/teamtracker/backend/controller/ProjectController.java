package com.teamtracker.backend.controller;

import com.teamtracker.backend.domain.Project;
import com.teamtracker.backend.domain.ProjectNameAndStatus;
import com.teamtracker.backend.domain.User;
import com.teamtracker.backend.service.MapValidationErrorService;
import com.teamtracker.backend.service.ProjectService;
import com.teamtracker.backend.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    Iterable<Project> projectsByOwnerName = projectService.saveProject(project);
//    List<String> projectNames = projectService.saveProject(project);
    return new ResponseEntity<Iterable<Project>>(projectsByOwnerName, HttpStatus.OK);
  }

//  @GetMapping("/getByProjectNameAndOwnerName")
//  public ResponseEntity<?> getProjectByProjectNameAndOwnerName(
//          @RequestParam(name = "projectName") String projectName,
//          @RequestParam(name = "ownerName") String ownerName
//  ) {
//    Project foundProject = projectService.findByOwnerNameAndProjectName(ownerName, projectName);
//    return new ResponseEntity<Project>(foundProject, HttpStatus.OK);
//  }

  @PostMapping("/byProjectNameAndOwnerName")
  public ResponseEntity<?> getProjectByProjectNameAndOwnerName(@Valid @RequestBody Map<String, String> jsonMap, BindingResult result) {
    ResponseEntity<?> errMap = mapValidationErrorService.MapValidationService(result);
    if (errMap != null) {
      return errMap;
    }
    String ownerName = jsonMap.get("ownerName");
    String projectName = jsonMap.get("projectName");

    Project foundProject = projectService.findByOwnerNameAndProjectName(ownerName, projectName);
    return new ResponseEntity<Project>(foundProject, HttpStatus.OK);
  }

//  没用的controller方法
  @GetMapping("/all")
  public ResponseEntity<Iterable<Project>> getAllProject() {
    Iterable<Project> allProject = projectService.findAllProject();
    return new ResponseEntity<Iterable<Project>>(allProject, HttpStatus.OK);
  }

  @DeleteMapping("/byProjectNameAndOwnerName")
  public ResponseEntity<?> deleteProjectByProjectNameAndOwnerName(
          @Valid @RequestBody Map<String,
                  String> jsonMap, BindingResult result
  ) {
    ResponseEntity<?> errMap = mapValidationErrorService.MapValidationService(result);
    if (errMap != null) {
      return errMap;
    }
    String ownerName = jsonMap.get("ownerName");
    String projectName = jsonMap.get("projectName");
    projectService.deleteProjectByProjectNameAndOwnerName(projectName, ownerName);
    // get the exact user
//    User owner = userService.findByUserName(ownerName);
//    Iterable<Project> projectsByOwner = projectService.findAllByOwnerName(ownerName);
    Iterable<ProjectNameAndStatus> foundProjects = projectService.ownedOrParticipated(ownerName);
    return new ResponseEntity<Iterable<ProjectNameAndStatus>>(foundProjects, HttpStatus.OK);
  }

  @GetMapping("/own/{ownerName}")
  public ResponseEntity<Iterable<Project>> getAllProjectByOwnerName(@PathVariable String ownerName) {
    Iterable<Project> projectsByOwner = projectService.findAllByOwnerName(ownerName);
    return new ResponseEntity<Iterable<Project>>(projectsByOwner, HttpStatus.OK);
  }

  @PostMapping("/engage")
  public ResponseEntity<?> engageProject(@Valid @RequestBody Map<String, String> jsonMap, BindingResult result) {
    ResponseEntity<?> errMap = mapValidationErrorService.MapValidationService(result);
    if (errMap != null) {
      return errMap;
    }
    String partnerName = jsonMap.get("participantName");
    String ownerName = jsonMap.get(("ownerName"));
    String projectName = jsonMap.get("projectName");

    Iterable<ProjectNameAndStatus> projectNameAndStatuses = projectService.addPartner(partnerName, ownerName, projectName);

    return new ResponseEntity<Iterable<ProjectNameAndStatus>>(projectNameAndStatuses, HttpStatus.OK);
  }

  @PostMapping("/notEngage")
  public ResponseEntity<?> notEngageProject(@Valid @RequestBody Map<String, String> jsonMap, BindingResult result) {
    ResponseEntity<?> errMap = mapValidationErrorService.MapValidationService(result);
    if (errMap != null) {
      return errMap;
    }
    String partnerName = jsonMap.get("participantName");
    String ownerName = jsonMap.get(("ownerName"));
    String projectName = jsonMap.get("projectName");

    Iterable<ProjectNameAndStatus> projectNameAndStatuses = projectService.deletePartner(partnerName, ownerName, projectName);

    return new ResponseEntity<Iterable<ProjectNameAndStatus>>(projectNameAndStatuses, HttpStatus.OK);
  }

  @PostMapping("/ownedOrParticipatedByUserName")
  public ResponseEntity<?> findAllProjectNameAndStatusByUserName(@Valid @RequestBody Map<String, String> jsonMap, BindingResult result) {
    ResponseEntity<?> errMap = mapValidationErrorService.MapValidationService(result);
    if (errMap != null) {
      return errMap;
    }
    String userName = jsonMap.get(("userName"));
    Iterable<ProjectNameAndStatus> projectNameAndStatuses = projectService.ownedOrParticipated(userName);
    return new ResponseEntity<Iterable<ProjectNameAndStatus>>(projectNameAndStatuses, HttpStatus.OK);
  }

  @GetMapping("/par/{parName}")
  public ResponseEntity<Iterable<Project>> getAllProjectByParName(@PathVariable String parName) {
    User partner = userService.findByUserName(parName);
    Iterable<Project> projectsByPartner = projectService.findAllByPartner(partner);
    return new ResponseEntity<Iterable<Project>>(projectsByPartner, HttpStatus.OK);
  }

  //user controller

  // 这个方法是通过Project得到这个project的partners
  @GetMapping("/collaborators")
  public ResponseEntity<?> getAllCollaborators(@Valid @RequestBody Project project, BindingResult result) {
    ResponseEntity<?> errMap = mapValidationErrorService.MapValidationService(result);
    if (errMap != null) {
      return errMap;
    }
    Iterable<User> collaborators = userService.getPartnersByProject(project);
    return new ResponseEntity<Iterable<User>>(collaborators, HttpStatus.OK);
  }

  @PostMapping("/search")
  public ResponseEntity<?> getAllProjectNamesBySearch(@Valid @RequestBody Map<String, String> jsonMap, BindingResult result) {
    ResponseEntity<?> errMap = mapValidationErrorService.MapValidationService(result);
    if (errMap != null) {
      return errMap;
    }
    String ownerName = jsonMap.get(("ownerName"));
    String searchedString = jsonMap.get("searchedString");
    Iterable<Project> foundProjects = projectService.searchByString(ownerName, searchedString);
    return new ResponseEntity<Iterable<Project>>(foundProjects, HttpStatus.OK);
  }


  // 这个方法是通过projectName和ownerName得到这个project的partners，先注释掉不用吧
//  @GetMapping("/partnersByProjectNameAndOwnerName")
//  public ResponseEntity<?> getAllCollaborators(@Valid @RequestBody Map<String, String> jsonMap, BindingResult result) {
//    ResponseEntity<?> errMap = mapValidationErrorService.MapValidationService(result);
//    if (errMap != null) {
//      return errMap;
//    }
//    String ownerName = jsonMap.get(("ownerName"));
//    String projectName = jsonMap.get("projectName");
//    Iterable<User> collaborators = userService.getPartnersByProjectNameAndOwnerName(projectName, ownerName);
//    return new ResponseEntity<Iterable<User>>(collaborators, HttpStatus.OK);
//  }

}
