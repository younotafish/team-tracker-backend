package com.teamtracker.backend.service;

import com.teamtracker.backend.domain.Project;
import com.teamtracker.backend.domain.ProjectTask;
import com.teamtracker.backend.repository.ProjectRepository;
import com.teamtracker.backend.repository.ProjectTaskRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

  @Autowired
  ProjectRepository projectRepository;
  @Autowired
  ProjectTaskRepository projectTaskRepository;

  public ProjectTask createOrUpdateTaskByIncomingTask(ProjectTask projectTask){
      Project project = projectRepository.findByOwnerNameAndProjectName(projectTask.getOwnerName(),projectTask.getProjectName());
      if(true){
        //是否为null
      }
      ProjectTask oldProjectTask = projectTaskRepository.
          findByProjectNameAndOwnerNameAndTaskName(projectTask.getProjectName(),projectTask.getOwnerName(),projectTask.getTaskName());
      if(oldProjectTask == null){
        projectTask.setUuid(UUID.randomUUID());
        projectTask.setProject(project);
        projectTaskRepository.save(projectTask);
        List<ProjectTask> oldTasks = project.getTasksContained();
        oldTasks.add(projectTask);
        projectRepository.save(project);
        return projectTask;
      }
      else{
        oldProjectTask.setTaskName(projectTask.getTaskName());
        oldProjectTask.setDescription(projectTask.getDescription());
        oldProjectTask.setStatus(oldProjectTask.getStatus());
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
}
