package com.teamtracker.backend.service;

import com.teamtracker.backend.domain.Project;
import com.teamtracker.backend.domain.User;
import com.teamtracker.backend.exceptions.ProjectNameException;
import com.teamtracker.backend.exceptions.ProjectNotFoundException;
import com.teamtracker.backend.repository.ProjectRepository;
import com.teamtracker.backend.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

  @Autowired
  private ProjectRepository projectRepository;

  @Autowired
  private UserRepository userRepository;


  public Project saveOrUpdateProject(Project project) {
    if (project.getProjectName() != null) {
      Project existingProject = projectRepository.findByProjectName(project.getProjectName());
      if (existingProject != null && (existingProject.getOwner().getUserName()
          .equals(project.getOwnerName()))) {
        return projectRepository.save(existingProject);
      }
//        else if(existingProject == null){
//          throw new ProjectNotFoundException("Project with name: "+ project.getProjectName()+ " can not be update");
//        }
    }
    try {
      User theUser = userRepository.findByUserName(project.getOwnerName());
      project.setOwner(theUser);
      return projectRepository.save(project);
    } catch (Exception exception) {
      throw new ProjectNameException(
          "Project with name " + project.getProjectName() + "already exist.");
    }


  }

  public Project findByProjectName(String projectName) {
    Project project = projectRepository.findByProjectName(projectName);
    if (project == null) {
      throw new ProjectNameException("Project name " + projectName + " does not exist.");
    }
    return project;
  }

  public Iterable<Project> findAllProject() {
    return projectRepository.findAll();
  }

  public void deleteProjectByName(String projectName) {
    Project project = projectRepository.findByProjectName(projectName);
    if (project == null) {
      throw new ProjectNameException(
          "Can not delete project with name " + projectName + ". This project does not exist");
    }
    projectRepository.delete(project);
  }

  public Iterable<Project> findAllByUser(User user) {

    return projectRepository.findByOwner(user);
  }

  public Iterable<Project> findAllByPar(User parUser) {
    return parUser.getProjectParticipated();
  }

  public Project findByOwnerNameAndProjectName(String ownerName, String projectName) {
    return projectRepository.findByOwnerNameAndProjectName(ownerName, projectName);
  }

  public Project addParticipant(String participantName, String ownerName, String projectName) {

    User participant = userRepository.findByUserName(participantName);

    Project project1 = projectRepository.findByOwnerNameAndProjectName(ownerName, projectName);
    List<User> pars = project1.getPartners();
    pars.add(participant);
    project1.setPartners(pars);

    return projectRepository.save(project1);
  }


}
