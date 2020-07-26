package com.teamtracker.backend.repository;

import com.teamtracker.backend.domain.ProjectTask;
import org.springframework.data.repository.CrudRepository;

public interface ProjectTaskRepository extends CrudRepository<ProjectTask,Long> {
  ProjectTask findByProjectNameAndOwnerNameAndTaskName(String projectName, String ownerName,String taskName);
  Iterable<ProjectTask> findByProjectNameAndOwnerName(String projectName, String ownerName);
}
