package com.teamtracker.backend.service;

import com.teamtracker.backend.domain.Project;
import com.teamtracker.backend.domain.ProjectTask;
import com.teamtracker.backend.exceptions.ProjectNotFoundException;
import com.teamtracker.backend.repository.ProjectRepository;
import com.teamtracker.backend.repository.ProjectTaskRepository;
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

  public ProjectTask createOrUpdateTaskByIncomingTask(ProjectTask projectTask){
      Project project = projectRepository.findByOwnerNameAndProjectName(projectTask.getOwnerName(),projectTask.getProjectName());
      if(project == null){
        throw new ProjectNotFoundException("project " + projectTask.getProjectName() + " not found.");
      }
      ProjectTask oldProjectTask = projectTaskRepository.
          findByProjectNameAndOwnerNameAndTaskName(projectTask.getProjectName(),projectTask.getOwnerName(),projectTask.getTaskName());
      if(oldProjectTask == null){
//      新建一个任务
//        projectTask.setUuid(UUID.randomUUID());
        projectTask.setProject(project);
        projectTaskRepository.save(projectTask);
        List<ProjectTask> oldTasks = project.getTasksContained();
        oldTasks.add(projectTask);
        projectRepository.save(project);
        return projectTask;
      }
      else{
//      更新一个任务
        oldProjectTask.setTaskName(projectTask.getTaskName());
        oldProjectTask.setTaskDescription(projectTask.getTaskDescription());
        oldProjectTask.setStatus(projectTask.getStatus());
        projectTaskRepository.save(oldProjectTask);
        return oldProjectTask;
      }
  }

  public Iterable<ProjectTask> getTasksByProject(Project project){
    String projectName = project.getProjectName();
    String ownerName = project.getOwnerName();
    Iterable<ProjectTask> tasksInProject = projectTaskRepository
        .findByProjectNameAndOwnerName(projectName, ownerName);
    return tasksInProject;

  }

  public Iterable<ProjectTask> getTasksTodoByProject(Project project){
      String projectName = project.getProjectName();
      String ownerName = project.getOwnerName();
      Iterable<ProjectTask> tasksInProject = projectTaskRepository
              .findByProjectNameAndOwnerNameAndStatus(projectName, ownerName, todo);
      return tasksInProject;
  }

    public Iterable<ProjectTask> getTasksDoingByProject(Project project){
        String projectName = project.getProjectName();
        String ownerName = project.getOwnerName();
        Iterable<ProjectTask> tasksInProject = projectTaskRepository
                .findByProjectNameAndOwnerNameAndStatus(projectName, ownerName, doing);
        return tasksInProject;
    }

    public Iterable<ProjectTask> getTasksDoneByProject(Project project){
        String projectName = project.getProjectName();
        String ownerName = project.getOwnerName();
        Iterable<ProjectTask> tasksInProject = projectTaskRepository
                .findByProjectNameAndOwnerNameAndStatus(projectName, ownerName, done);
        return tasksInProject;
    }
}
