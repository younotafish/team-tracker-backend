package com.teamtracker.backend.repository;

import com.teamtracker.backend.domain.Project;
import com.teamtracker.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project,Long> {
  Iterable<Project> findByOwnerName(String ownerName);
  Project findByOwnerNameAndProjectName(String ownerName, String projectName);
//  Project deleteByProjectNameAndOwnerName(String projectName,String ownerName);
  Iterable<Project> findByOwnerNameAndProjectNameContains(String ownerName,String searchedString);
  Iterable<Project> findByOwnerNameAndProjectDescriptionContains(String ownerName, String searchedString);


}
