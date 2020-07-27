package com.teamtracker.backend.service;

import com.teamtracker.backend.domain.Project;
import com.teamtracker.backend.domain.User;
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


  public Iterable<User> getCollaboratorsByProject(Project project){
    Project project1 = projectRepository.findByOwnerNameAndProjectName(project.getOwnerName(),project.getProjectName());
    List<User> partners = project1.getPartners();
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
