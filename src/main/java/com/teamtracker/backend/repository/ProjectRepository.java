package com.teamtracker.backend.repository;

import com.teamtracker.backend.domain.Project;
import com.teamtracker.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project,Long> {
  Iterable<Project> findByOwner(User owner);
  Project findByOwnerNameAndProjectName(String ownerName, String projectName);
  Iterable<Project> deleteByProjectNameAndOwnerName(String projectName,String ownerName);

}
