package com.teamtracker.backend.service;

import com.teamtracker.backend.domain.Project;
import com.teamtracker.backend.domain.User;
import com.teamtracker.backend.exceptions.ProjectNameException;
import com.teamtracker.backend.exceptions.ProjectNotFoundException;
import com.teamtracker.backend.exceptions.UserNameException;
import com.teamtracker.backend.repository.ProjectRepository;
import com.teamtracker.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  ProjectRepository projectRepository;

  public User findByUserName(String userName){
    User user = userRepository.findByUserName(userName);
    if(user == null){
      User newUser = new User(userName);
      return userRepository.save(newUser);
    }
    return user;
  }


  public Iterable<User> getPartnersByProject(Project project){
    Project foundProject = projectRepository.findByOwnerNameAndProjectName(project.getOwnerName(),project.getProjectName());
    if (foundProject == null) {
      throw new ProjectNotFoundException("The project" + project.getProjectName() + " does not exist.");
    }
    List<User> partners = foundProject.getPartners();
    return partners;
  }

  public Iterable<User> getPartnersByProjectNameAndOwnerName(String projectName, String ownerName){
    Project foundProject = projectRepository.findByOwnerNameAndProjectName(ownerName, projectName);
    if (foundProject == null) {
      throw new ProjectNotFoundException("The project" + projectName + " does not exist.");
    }
    List<User> partners = foundProject.getPartners();
    return partners;
  }

//  public User findByParName(String parName){
//    User user = userRepository.findByUserName(parName);
//    if(user == null){
//      throw new UserNameException("User with name: "+ userName+" does not exist");
//    }
//    return user;
//  }
}
