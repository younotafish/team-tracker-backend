package com.teamtracker.backend.repository;

import com.teamtracker.backend.domain.ProjectTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectTaskRepository extends JpaRepository<ProjectTask,Long> {
  ProjectTask findByProjectNameAndOwnerNameAndTaskName(String projectName, String ownerName,String taskName);
  Iterable<ProjectTask> findByProjectNameAndOwnerName(String projectName, String ownerName);
  Iterable<ProjectTask> findByProjectNameAndOwnerNameAndStatus(String projectName, String ownerName, String status);
}
