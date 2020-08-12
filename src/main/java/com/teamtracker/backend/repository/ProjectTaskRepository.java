package com.teamtracker.backend.repository;

import com.teamtracker.backend.domain.ProjectTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ProjectTaskRepository extends CrudRepository<ProjectTask,Long> {
  ProjectTask findByProjectNameAndOwnerNameAndTaskName(String projectName, String ownerName,String taskName);
  Iterable<ProjectTask> findByProjectNameAndOwnerName(String projectName, String ownerName);
  Iterable<ProjectTask> findByProjectNameAndOwnerNameAndStatus(String projectName, String ownerName, String status);

  Iterable<ProjectTask> findByOwnerNameAndTaskNameContains(String ownerName, String searchedString);
  Iterable<ProjectTask> findByOwnerNameAndTaskDescriptionContains(String ownerName, String searchedString);

  Iterable<ProjectTask> findByOwnerNameAndProjectNameAndTaskNameContains(String ownerName, String projectName, String searchedString);
  Iterable<ProjectTask> findByOwnerNameAndProjectNameAndTaskDescriptionContains(String ownerName, String projectName, String searchedString);
}
