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
        // 更新project里面的tasksContained
        List<ProjectTask> oldTasks = project.getTasksContained();
        oldTasks.add(projectTask);
      }
      else{
//      更新一个任务
        oldProjectTask.setTaskName(projectTask.getTaskName());
        oldProjectTask.setTaskDescription(projectTask.getTaskDescription());
        oldProjectTask.setStatus(projectTask.getStatus());
        List<ProjectTask> oldTasks = project.getTasksContained();
        // 更新project里面的tasksContained
        for (ProjectTask task: oldTasks) {
            if (task.getOwnerName() == oldProjectTask.getOwnerName()
                    && task.getProjectName() == oldProjectTask.getProjectName()) {
                oldTasks.remove(task);
                oldTasks.add(oldProjectTask);
                break;
            }
        }
      }
      projectRepository.save(project);
      ProjectTask savedProject = projectTaskRepository.save(projectTask);
      return savedProject;
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
