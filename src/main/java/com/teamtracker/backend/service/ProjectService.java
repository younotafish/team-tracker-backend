package com.teamtracker.backend.service;

import com.teamtracker.backend.domain.Project;
import com.teamtracker.backend.domain.ProjectNameAndStatus;
import com.teamtracker.backend.domain.ProjectTask;
import com.teamtracker.backend.domain.User;
import com.teamtracker.backend.exceptions.ProjectNameException;
import com.teamtracker.backend.exceptions.ProjectNotFoundException;
import com.teamtracker.backend.exceptions.UserNameException;
import com.teamtracker.backend.repository.ProjectRepository;
import com.teamtracker.backend.repository.ProjectTaskRepository;
import com.teamtracker.backend.repository.UserRepository;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.lang.Thread.sleep;

@Service
@Transactional
public class ProjectService {

  private final String owned = "owned";
  private final String participated = "participated";

  @Autowired
  private ProjectRepository projectRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ProjectTaskRepository projectTaskRepository;

  public Iterable<Project> saveProject(Project project) {
      Project existingProject = projectRepository
          .findByOwnerNameAndProjectName(project.getOwnerName(), project.getProjectName());
      // 如果数据库里已经有这个project（根据ownerName和projectName唯一确定），
      // 修改projectDescription
      if (existingProject != null) {
//        throw new ProjectNameException(
//            "The project " + project.getProjectName() + " in this account has already existed.");
          existingProject.setProjectDescription(project.getProjectDescription());
          projectRepository.save(existingProject);
      }
      // 否则，数据库里以前没存过这个project，那么就创建这个project
      else {
        // 先确定这个project的owner
        User owner = userRepository.findByUserName(project.getOwnerName());
        // 检查数据库中有没有存过这个owner
        if (owner == null) {
          owner = new User(project.getOwnerName());
        }
        project.setOwner(owner);
        User savedOwner = userRepository.save(owner);
        projectRepository.save(project);
      }
      Iterable<Project> allProjects = projectRepository.findByOwnerName(project.getOwnerName());
      return allProjects;
      //      List<String> projectNames = new ArrayList<>();
//      for (Project foundProject: allProjects) {
//        projectNames.add(foundProject.getProjectName());
//      }
//      return projectNames;
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
    // 如果没有这个user，自动创建一个user
    User owner = userRepository.findByUserName(ownerName);
    if (owner == null) {
      User newUser = new User(ownerName);
      owner = userRepository.save(newUser);
      // 返回个空的呗
      List<Project> list = new ArrayList<>();
      Iterable<Project> iterable = list;
      return iterable;
    } else {
      Iterable<Project> projectsByOwnerName = projectRepository.findByOwnerName(ownerName);
      return projectsByOwnerName;
    }
  }

  public List<ProjectNameAndStatus> addPartner(String partnerName, String ownerName, String projectName) {
    User partner = userRepository.findByUserName(partnerName);
    // 如果partner没有，抛出异常
    if (partner == null) {
//       throw new UserNameException("The partner" + partner.getUserName() + " doest not exist.");
      partner = new User(partnerName);
      userRepository.save(partner);
    }
    Project foundProject = projectRepository.findByOwnerNameAndProjectName(ownerName, projectName);
    if (foundProject == null) {
      throw new ProjectNotFoundException("Project " + projectName + " cannot be found.");
    }
    List<User> partners = foundProject.getPartners();
    // 不能重复添加，使重复添加的效果失效
    boolean repeatAdd = false;
    // 我是我自己项目的partner，肯定不能这么干
    if (ownerName.equals(partnerName)) {
      repeatAdd = true;
    }
//    System.out.println("partnerName: " + partnerName);
    for (User user: partners) {
//      System.out.println("existingPartnerName: " + user.getUserName());
      if (user.getUserName().equals(partnerName)) {
        repeatAdd = true;
        break;
      }
    }
//    System.out.println(repeatAdd);
    if (repeatAdd == false) {
      partners.add(partner);
      foundProject.setPartners(partners);
      projectRepository.save(foundProject);
    }
    // 返回这个partner这个人拥有的或者参与的所有projectName + status of "owned" or "participated"
    List<ProjectNameAndStatus> projectNameAndStatusList = new ArrayList<>();
    Iterable<Project> projectsOwned = partner.getProjectOwned();
    for (Project project: projectsOwned) {
      ProjectNameAndStatus projectNameAndStatus = new ProjectNameAndStatus(project.getProjectName(), owned, project.getProjectDescription());
      projectNameAndStatusList.add(projectNameAndStatus);
    }
    Iterable<Project> projectsParticipated = partner.getProjectParticipated();
    for (Project project: projectsParticipated) {
      ProjectNameAndStatus projectNameAndStatus = new ProjectNameAndStatus(project.getProjectName(), participated, project.getProjectDescription());
      projectNameAndStatusList.add(projectNameAndStatus);
    }
    if (repeatAdd == false) {
      projectNameAndStatusList.add(new ProjectNameAndStatus(projectName, participated, foundProject.getProjectDescription()));
    }
    return projectNameAndStatusList;
  }

  public List<ProjectNameAndStatus> deletePartner(String partnerName, String ownerName, String projectName) {
    User partner = userRepository.findByUserName(partnerName);
    // 如果partner没有，抛出异常
    if (partner == null) {
//       throw new UserNameException("The partner" + partner.getUserName() + " doest not exist.");
      partner = new User(partnerName);
      userRepository.save(partner);
    }
    Project foundProject = projectRepository.findByOwnerNameAndProjectName(ownerName, projectName);
    if (foundProject == null) {
      throw new ProjectNotFoundException("Project " + projectName + " cannot be found.");
    }
    List<User> partners = foundProject.getPartners();
    partners.remove(partner);
    foundProject.setPartners(partners);
    Project savedProject = projectRepository.save(foundProject);
    // 返回这个partner这个人拥有的或者参与的所有projectName + status of "owned" or "participated"
    List<ProjectNameAndStatus> projectNameAndStatusList = new ArrayList<>();
    Iterable<Project> projectsOwned = partner.getProjectOwned();
    for (Project project: projectsOwned) {
      ProjectNameAndStatus projectNameAndStatus = new ProjectNameAndStatus(project.getProjectName(), owned, project.getProjectDescription());
      projectNameAndStatusList.add(projectNameAndStatus);
    }
    Iterable<Project> projectsParticipated = partner.getProjectParticipated();
    for (Project project: projectsParticipated) {
      if (project.getProjectName().equals(projectName)) {continue;}
      ProjectNameAndStatus projectNameAndStatus = new ProjectNameAndStatus(project.getProjectName(), participated, project.getProjectDescription());
      projectNameAndStatusList.add(projectNameAndStatus);
    }
    return projectNameAndStatusList;
  }
  // ownedOrParticipated
  public List<ProjectNameAndStatus> ownedOrParticipated(String userName) {
    User user = userRepository.findByUserName(userName);
    // 如果user没有，抛出异常
    if (user == null) {
      // 这个我不知道写的对不对?
      throw new UserNameException("The user" + user.getUserName() + " doest not exist.");
    }
    // 返回这个user拥有的或者参与的所有projectName + status of "owned" or "participated"
    List<ProjectNameAndStatus> projectNameAndStatusList = new ArrayList<>();
    Iterable<Project> projectsOwned = user.getProjectOwned();
    for (Project project: projectsOwned) {
      ProjectNameAndStatus projectNameAndStatus = new ProjectNameAndStatus(project.getProjectName(), owned, project.getProjectDescription());
      projectNameAndStatusList.add(projectNameAndStatus);
    }
    Iterable<Project> projectsParticipated = user.getProjectParticipated();
    for (Project project: projectsParticipated) {
      ProjectNameAndStatus projectNameAndStatus = new ProjectNameAndStatus(project.getProjectName(), participated, project.getProjectDescription());
      projectNameAndStatusList.add(projectNameAndStatus);
    }
    return projectNameAndStatusList;
  }

  public Iterable<Project> findAllByPartner(User partner) {
    List<Project> projectParticipated = partner.getProjectParticipated();
    return projectParticipated;
  }

  public Iterable<Project> searchByString(String ownerName, String searchedString) {
    Iterable<Project> foundProjects;
    if (searchedString.length() == 0) {
      foundProjects = new ArrayList<>();
      return foundProjects;
    }
    Set<Project> set = new HashSet<>();
    // 下面都是该用户拥有的projet进行搜索

    // 对project的字段进行搜索
    Iterable<Project> projectsByProjectNameContains = projectRepository.findByOwnerNameAndProjectNameContains(ownerName, searchedString);
    for (Project project: projectsByProjectNameContains) {
      set.add(project);
    }
    Iterable<Project> projectsByProjectDescriptionContains = projectRepository.findByOwnerNameAndProjectDescriptionContains(ownerName, searchedString);
    for (Project project: projectsByProjectDescriptionContains) {
      set.add(project);
    }
    // 对project中task的字段进行搜索
    //    Iterable<ProjectTask> projectsByTaskNameContains = projectTaskRepository.findByOwnerNameAndTaskNameContains(ownerName, searchedString);
    //    for (ProjectTask task: projectsByTaskNameContains) {
    //      set.add(task.getProject());
    //    }
    //    Iterable<ProjectTask> projectsByTaskDescriptionContains = projectTaskRepository.findByOwnerNameAndTaskDescriptionContains(ownerName, searchedString);
    //    for (ProjectTask task: projectsByTaskDescriptionContains) {
    //      set.add(task.getProject());
    //    }

    // 下面是对该用户参与的项目进行搜索
    User user = userRepository.findByUserName(ownerName);
    for (Project project: user.getProjectParticipated()) {
      if (kmp(project.getProjectName(), searchedString) || kmp(project.getProjectDescription(), searchedString)) {
        set.add(project);
      }
    }

    foundProjects = set;
    return foundProjects;
//    Iterable<Project> projects = projectRepository.findByOwnerName(ownerName);
//    List<String> projectNames = new ArrayList<>();
//    for (Project project: projects) {
//      Set<String> set = new HashSet<>();
//      set.add(project.getProjectName());
//      set.add(project.getProjectDescription());
//      List<ProjectTask> tasks = project.getTasksContained();
//      for (ProjectTask task: tasks) {
//        set.add(task.getTaskName());
//        set.add(task.getTaskDescription());
//      }
//      if (set.contains(searchedString)) {
//        projectNames.add(project.getProjectName());
//      }
//    }

  }

  // 对参与的project写一个kmp算法匹配字段
  private boolean kmp(String s, String t) {
    int m = s.length(), n = t.length();
    if (n == 0) {return true;}
    if (m == 0) {return false;}
    // 构建next[]数组
    int k = -1, j = 0;
    int[] next = new int[n];
    next[0] = -1;
    while (j < n - 1) {
      if (k == -1 || s.charAt(j) == s.charAt(k)) {
        ++k;
        ++j;
        next[j] = s.charAt(j) != s.charAt(k) ? k : next[k];
      } else {
        k = next[k];
      }
    }
    int i = 0;
    j = 0;
    while (i < m && j < n) {
      if (j == - 1 || s.charAt(i) == t.charAt(j)) {
        ++i; ++j;
      } else {
        j = next[j];
      }
    }
    int res = (j == n) ? i - j : -1;
    return res != -1;
  }


}
