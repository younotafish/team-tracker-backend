package com.teamtracker.backend.repository;

import com.teamtracker.backend.domain.Project;
import com.teamtracker.backend.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project,Long> {
  Project findByProjectName(String projectName);
  Iterable<Project> findByOwner(User owner);
  Project findByOwnerNameAndProjectName(String ownerName, String projectName);
}
