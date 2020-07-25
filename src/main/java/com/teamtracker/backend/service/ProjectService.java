package com.teamtracker.backend.service;

import com.teamtracker.backend.domain.Project;
import com.teamtracker.backend.exceptions.ProjectNameException;
import com.teamtracker.backend.repository.ProjectRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
  @Autowired
  private ProjectRepository projectRepository;


  public Project saveOrUpdateProject(Project project){
      try {
        project.setProjectName(project.getProjectName());
        return projectRepository.save(project);
      }
      catch (Exception exception){
        throw new ProjectNameException("Project name " + project.getProjectName() + " already exist.");
      }
  }

  public Project findByProjectName(String projectName){
    Project project = projectRepository.findByProjectName(projectName);
    if(project == null){
      throw new ProjectNameException("Project name "+ projectName+" does not exist.");
    }
    return project;
  }

  public Iterable<Project> findAllProject(){
    return projectRepository.findAll();
  }

  public void deleteProjectByName(String projectName){
    Project project = projectRepository.findByProjectName(projectName);
    if(project == null){
      throw  new ProjectNameException("Can not delete project with name "+ projectName + ". This project does not exist");
    }
    projectRepository.delete(project);
  }
}
