package com.teamtracker.backend.service;

import com.teamtracker.backend.domain.Project;
import com.teamtracker.backend.domain.ProjectTask;
import com.teamtracker.backend.exceptions.ProjectNotFoundException;
import com.teamtracker.backend.repository.ProjectRepository;
import com.teamtracker.backend.repository.ProjectTaskRepository;

import java.util.*;

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
//        Iterable<ProjectTask> updatedtasksContained = project.getTasksContained();
//        List<String> taskNames = new ArrayList<>();
//        for (ProjectTask updatedTaskContained: updatedtasksContained) {
//            taskNames.add(updatedTaskContained.getTaskName());
//        }
        String status = projectTask.getStatus();
        List<String> taskNames;
        if (status.equals(todo)) {
            taskNames = getTasksTodoByProject(project);
        } else if (status.equals(doing)) {
            taskNames = getTasksDoingByProject(project);
        } else if (status.equals(done)) {
            taskNames = getTasksDoneByProject(project);
        } else {
            taskNames = new ArrayList<>();
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

    public List<String> deleteByExistingTask (ProjectTask inputTask) {
        ProjectTask existingTask = projectTaskRepository.findByProjectNameAndOwnerNameAndTaskName(inputTask.getProjectName(), inputTask.getOwnerName(), inputTask.getTaskName());
        if (existingTask == null) {
            // 抛出异常，没这个任务
            // 这里应该写一个taskNotFoundException的
            throw new ProjectNotFoundException("Task " + inputTask.getTaskName() + " cannot be found.");
        }
        projectTaskRepository.delete(existingTask);
        List<String> taskNames = new ArrayList<>();
        // 如果没有status这个字段，或者和数据库里的status字段不匹配，直接抛出异常
        // 这里应该写一个StatusNotMatchedException的
        if (inputTask.getStatus() == null || !inputTask.getStatus().equals(existingTask.getStatus())) {
            throw new ProjectNotFoundException("The status of input task doesn't match the existing task in the repository.");
        }
        Iterable<ProjectTask> foundTasks = projectTaskRepository.findByProjectNameAndOwnerNameAndStatus(inputTask.getProjectName(), inputTask.getOwnerName(), inputTask.getStatus());
        for (ProjectTask task: foundTasks) {
            taskNames.add(task.getTaskName());
        }
        return taskNames;
    }

    public List<String> updateTaskByOriginalTaskName(String projectName,
                                                     String ownerName,
                                                     String taskName,
                                                     String taskDescription,
                                                     String status,
                                                     String originalTaskName){
        // 可以加一个对owner是否为null的验证以及抛出异常，也可以不加
        Project project = projectRepository.findByOwnerNameAndProjectName(ownerName, projectName);
        if(project == null){
            throw new ProjectNotFoundException("project " + projectName + " not found.");
        }
        ProjectTask oldProjectTask = projectTaskRepository.findByProjectNameAndOwnerNameAndTaskName(projectName, ownerName, originalTaskName);
        if(oldProjectTask == null){
            // 抛出异常，没这个任务
            // 这里应该写一个taskNotFoundException的
            throw new ProjectNotFoundException("task " + originalTaskName + " not found.");
        }
        else{
            // 更新一个任务
            oldProjectTask.setTaskName(taskName);
            oldProjectTask.setTaskDescription(taskDescription);
            oldProjectTask.setStatus(status);
            projectTaskRepository.save(oldProjectTask);
        }
        List<String> taskNames;
        if (status.equals(todo)) {
            taskNames = getTasksTodoByProject(project);
        } else if (status.equals(doing)) {
            taskNames = getTasksDoingByProject(project);
        } else if (status.equals(done)) {
            taskNames = getTasksDoneByProject(project);
        } else {
            // 其实应该写一个statusNotFoundException的，再优化吧
            taskNames = new ArrayList<>();
        }
        return taskNames;
    }

    public Iterable<ProjectTask> searchByString(String ownerName, String projectName, String searchedString) {
        Set<ProjectTask> set = new HashSet<>();
        Iterable<ProjectTask> tasksByTaskNameContains = projectTaskRepository.findByOwnerNameAndProjectNameAndTaskNameContains(ownerName, projectName, searchedString);
        for (ProjectTask task : tasksByTaskNameContains) {
            set.add(task);
        }
        Iterable<ProjectTask> tasksByTaskDescriptionContains = projectTaskRepository.findByOwnerNameAndProjectNameAndTaskDescriptionContains(ownerName, projectName, searchedString);
        for (ProjectTask task : tasksByTaskDescriptionContains) {
            set.add(task);
        }
        Iterable<ProjectTask> foundTasks = set;
        return foundTasks;
    }

    public List<List<String>> moveTaskStatus(String projectName, String ownerName, String taskName, String newStatus) {
        ProjectTask foundTask = projectTaskRepository.findByProjectNameAndOwnerNameAndTaskName(projectName, ownerName, taskName);
        // 抛出异常，没这个任务
        // 这里应该写一个taskNotFoundException的
        if (foundTask == null) {
            throw new ProjectNotFoundException("Task " + taskName + " cannot be found.");
        }
        String oldStatus = foundTask.getStatus();
        // 判断oldStatus和newStatus是否相同
        boolean isStatusesEqual = false;
        if (oldStatus.equals(newStatus)) {
            isStatusesEqual = true;
        }
        // 如果没有这个新状态既不是todo也不是doing也不是done，抛出异常
        // 其实应该写一个statusNotFoundException的，再优化吧
        else if (!newStatus.equals(todo) && !newStatus.equals(doing) && newStatus.equals(done)) {
            throw new ProjectNotFoundException("Invalid new status: " + newStatus);
        } else {
            foundTask.setStatus(newStatus);
            ProjectTask savedTask = projectTaskRepository.save(foundTask);
        }
        // 将moved过的oldTasks和newTasks返回
//        List<ProjectTask> oldTasks = new ArrayList();
//        Iterable<ProjectTask> oldStatusTasks = projectTaskRepository.findByProjectNameAndOwnerNameAndStatus(projectName, ownerName, oldStatus);
//        for (ProjectTask task: oldStatusTasks) {
//            oldTasks.add(task);
//        }
//        List<ProjectTask> newTasks = new ArrayList<>();
//        if (isStatusesEqual == false) {
//            Iterable<ProjectTask> newStatusTasks = projectTaskRepository.findByProjectNameAndOwnerNameAndStatus(projectName, ownerName, newStatus);
//            for (ProjectTask task: newStatusTasks) {
//                newTasks.add(task);
//            }
//        }
//        List<List<ProjectTask>> result = new ArrayList<>();
//        result.add(oldTasks);
//        result.add(newTasks);

        List<String> oldTasks = new ArrayList<>();
        Iterable<ProjectTask> oldStatusTasks = projectTaskRepository.findByProjectNameAndOwnerNameAndStatus(projectName, ownerName, oldStatus);
        for (ProjectTask task: oldStatusTasks) {
            oldTasks.add(task.getTaskName());
        }
        List<String> newTasks = new ArrayList<>();
        if (isStatusesEqual == false) {
            Iterable<ProjectTask> newStatusTasks = projectTaskRepository.findByProjectNameAndOwnerNameAndStatus(projectName, ownerName, newStatus);
            for (ProjectTask task: newStatusTasks) {
                newTasks.add(task.getTaskName());
            }
        }
        List<List<String>> result = new ArrayList<>();
        result.add(oldTasks);
        result.add(newTasks);
        return result;
    }
}
