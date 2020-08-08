package com.teamtracker.backend.repository;

import com.teamtracker.backend.domain.Project;
import com.teamtracker.backend.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project,Long> {
  Iterable<Project> findByOwner(User owner);
  Project findByOwnerNameAndProjectName(String ownerName, String projectName);
  Iterable<Project> deleteByProjectNameAndOwnerName(String projectName,String ownerName);
  Iterable<Project> findByOwnerNameAndProjectNameContains(String ownerName,String partialProjectName);

}
