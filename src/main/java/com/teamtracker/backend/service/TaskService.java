package com.teamtracker.backend.service;

import com.teamtracker.backend.domain.Project;
import com.teamtracker.backend.domain.ProjectTask;
import com.teamtracker.backend.exceptions.ProjectNotFoundException;
import com.teamtracker.backend.repository.ProjectRepository;
import com.teamtracker.backend.repository.ProjectTaskRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TaskService {

  final static String todo = "Todo";
  final static String doing = "Doing";
  final static String done = "Done";

  @Autowired
  ProjectRepository projectRepository;
  @Autowired
  ProjectTaskRepository projectTaskRepository;

//  public Iterable<ProjectTask> createOrUpdateTaskByIncomingTask(ProjectTask projectTask){
//      // 可以加一个对owner是否为null的验证以及抛出异常，也可以不加
//      Project project = projectRepository.findByOwnerNameAndProjectName(projectTask.getOwnerName(),projectTask.getProjectName());
//      if(project == null){
//        throw new ProjectNotFoundException("project " + projectTask.getProjectName() + " not found.");
//      }
//      ProjectTask oldProjectTask = projectTaskRepository.
//          findByProjectNameAndOwnerNameAndTaskName(projectTask.getProjectName(),projectTask.getOwnerName(),projectTask.getTaskName());
//      if(oldProjectTask == null){
////      新建一个任务
//        projectTask.setProject(project);
//        projectTaskRepository.save(projectTask);
//      }
//      else{
////      更新一个任务
//        oldProjectTask.setTaskName(projectTask.getTaskName());
//        oldProjectTask.setTaskDescription(projectTask.getTaskDescription());
//        oldProjectTask.setStatus(projectTask.getStatus());
//        projectTaskRepository.save(oldProjectTask);
//      }
//      Iterable<ProjectTask> updatedtasksContained = project.getTasksContained();
//      return updatedtasksContained;
//  }

    public List<String> createOrUpdateTaskByIncomingTask(ProjectTask projectTask){
        // 可以加一个对owner是否为null的验证以及抛出异常，也可以不加
        Project project = projectRepository.findByOwnerNameAndProjectName(projectTask.getOwnerName(),projectTask.getProjectName());
        if(project == null){
            throw new ProjectNotFoundException("project " + projectTask.getProjectName() + " not found.");
        }
        ProjectTask oldProjectTask = projectTaskRepository.
                findByProjectNameAndOwnerNameAndTaskName(projectTask.getProjectName(),projectTask.getOwnerName(),projectTask.getTaskName());
        if(oldProjectTask == null){
//      新建一个任务
            projectTask.setProject(project);
            projectTaskRepository.save(projectTask);
        }
        else{
//      更新一个任务
            oldProjectTask.setTaskName(projectTask.getTaskName());
            oldProjectTask.setTaskDescription(projectTask.getTaskDescription());
            oldProjectTask.setStatus(projectTask.getStatus());
            projectTaskRepository.save(oldProjectTask);
        }
        Iterable<ProjectTask> updatedtasksContained = project.getTasksContained();
        List<String> taskNames = new ArrayList<>();
        for (ProjectTask updatedTaskContained: updatedtasksContained) {
            taskNames.add(updatedTaskContained.getTaskName());
        }
        return taskNames;
    }

  public Iterable<ProjectTask> getTasksByProject(Project project){
    String projectName = project.getProjectName();
    String ownerName = project.getOwnerName();
    Iterable<ProjectTask> tasksInProject = projectTaskRepository
        .findByProjectNameAndOwnerName(projectName, ownerName);
    return tasksInProject;

  }

  public List<String> getTasksTodoByProject(Project project){
      String projectName = project.getProjectName();
      String ownerName = project.getOwnerName();
      Iterable<ProjectTask> tasksInProject = projectTaskRepository
              .findByProjectNameAndOwnerNameAndStatus(projectName, ownerName, todo);
      List<String> taskNames = new ArrayList<>();
      Iterator<ProjectTask> iterator = tasksInProject.iterator();
      while (iterator.hasNext()) {
          ProjectTask taskInProject = iterator.next();
          taskNames.add(taskInProject.getTaskName());
      }
      return taskNames;
  }

    public List<String> getTasksDoingByProject(Project project){
        String projectName = project.getProjectName();
        String ownerName = project.getOwnerName();
        Iterable<ProjectTask> tasksInProject = projectTaskRepository
                .findByProjectNameAndOwnerNameAndStatus(projectName, ownerName, doing);
        List<String> taskNames = new ArrayList<>();
        Iterator<ProjectTask> iterator = tasksInProject.iterator();
        while (iterator.hasNext()) {
            ProjectTask taskInProject = iterator.next();
            taskNames.add(taskInProject.getTaskName());
        }
        return taskNames;
    }

    public List<String> getTasksDoneByProject(Project project){
        String projectName = project.getProjectName();
        String ownerName = project.getOwnerName();
        Iterable<ProjectTask> tasksInProject = projectTaskRepository
                .findByProjectNameAndOwnerNameAndStatus(projectName, ownerName, done);
        List<String> taskNames = new ArrayList<>();
        Iterator<ProjectTask> iterator = tasksInProject.iterator();
        while (iterator.hasNext()) {
            ProjectTask taskInProject = iterator.next();
            taskNames.add(taskInProject.getTaskName());
        }
        return taskNames;
    }
}
