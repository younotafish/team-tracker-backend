package com.teamtracker.backend.service;

import com.teamtracker.backend.domain.Project;
import com.teamtracker.backend.domain.User;
import com.teamtracker.backend.exceptions.ProjectNameException;
import com.teamtracker.backend.exceptions.ProjectNotFoundException;
import com.teamtracker.backend.exceptions.UserNameException;
import com.teamtracker.backend.repository.ProjectRepository;
import com.teamtracker.backend.repository.UserRepository;

import java.util.ArrayList;
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
      // 如果数据库里已经有这个project（根据ownerName和projectName唯一确定），抛出异常
      Project existingProject = projectRepository
          .findByOwnerNameAndProjectName(project.getOwnerName(), project.getProjectName());
      if (existingProject != null) {
        throw new ProjectNameException(
            "The project " + project.getProjectName() + " in this account has already existed.");
      }
    // 先确定这个project的owner
    User owner = userRepository.findByUserName(project.getOwnerName());
    // owner之前没有在数据库中存过
    if (owner == null) {
      owner = new User(project.getOwnerName());
      List<Project> projectList = new ArrayList<>();
      projectList.add(project);
      owner.setProjectOwned(projectList);
      project.setOwner(owner);
    // owner在数据库中有过，也就是这个owner有过别的项目
    } else {
      List<Project> projectList = owner.getProjectOwned();
      projectList.add(project);
      owner.setProjectOwned(projectList);
      project.setOwner(owner);
    }
    User savedOwner = userRepository.save(owner);
    Project savedProject = projectRepository.save(project);
    return savedProject;
  }

  public Project findByOwnerNameAndProjectName(String ownerName, String projectName) {
    Project foundProject = projectRepository.findByOwnerNameAndProjectName(ownerName, projectName);
    if (foundProject == null) {
      throw new ProjectNameException("Project " + projectName + " does not exist.");
    }
    return foundProject;
  }

  //  没用的service方法
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
    return;
  }

  public Iterable<Project> findAllByOwnerName(String ownerName) {
    return projectRepository.findByOwnerName(ownerName);
  }

  public Project addPartner(String partnerName, String ownerName, String projectName) {

    User partner = userRepository.findByUserName(partnerName);
    // 如果partner没有，那怎么添加嘛，添加不了啊
    if (partner == null) {
      // 这个我不知道写的对不对
      throw new UserNameException("The partner" + partner.getUserName() + " doest not exist.");
    }
    Project foundProject = projectRepository.findByOwnerNameAndProjectName(ownerName, projectName);
    List<User> partners = foundProject.getPartners();
    partners.add(partner);
    foundProject.setPartners(partners);
    Project savedProject = projectRepository.save(foundProject);
    return savedProject;
  }

  public Iterable<Project> findAllByPartner(User partner) {
    List<Project> projectParticipated = partner.getProjectParticipated();
    return projectParticipated;

  }


}
