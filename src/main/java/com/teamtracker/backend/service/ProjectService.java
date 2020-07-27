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
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProjectService {

  @Autowired
  private ProjectRepository projectRepository;

  @Autowired
  private UserRepository userRepository;


  public Project saveProject(Project project) {
    if (project.getProjectName() != null) {
      Project existingProject = projectRepository
          .findByOwnerNameAndProjectName(project.getOwnerName(), project.getProjectName());
      if (existingProject != null) {
        throw new ProjectNameException(
            "The project " + project.getProjectName() + " in this account has already existed.");
      }
    }

    User theUser = userRepository.findByUserName(project.getOwnerName());
    project.setOwner(theUser);
    return projectRepository.save(project);
  }

  public Project findByOwnerNameAndProjectName(String ownerName, String projectName) {
    Project foundProject = projectRepository.findByOwnerNameAndProjectName(ownerName, projectName);
    if (foundProject == null) {
      throw new ProjectNameException("Project " + projectName + " does not exist.");
    }
    return foundProject;
  }

  //  没用
  public Iterable<Project> findAllProject() {
    return projectRepository.findAll();
  }

  public void deleteProjectByProjectNameAndOwnerName(String projectName,
      String ownerName) {
    Project foundProject = projectRepository.findByOwnerNameAndProjectName(ownerName, projectName);
    if (foundProject == null) {
      throw new ProjectNameException(
          "Can not delete project " + projectName + " because this project does not exist");
    }
    projectRepository.delete(foundProject);
  }

  public Iterable<Project> findAllByUser(User user) {

    return projectRepository.findByOwner(user);
  }

  public Iterable<Project> findAllByPar(User parUser) {
    return parUser.getProjectParticipated();
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
