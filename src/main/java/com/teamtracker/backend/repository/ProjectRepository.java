package com.teamtracker.backend.repository;

import com.teamtracker.backend.domain.Project;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project,Long> {
  Project findByProjectName(String projectName);
}
