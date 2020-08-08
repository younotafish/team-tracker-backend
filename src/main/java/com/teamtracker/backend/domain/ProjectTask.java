package com.teamtracker.backend.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotNull;

@Entity
public class ProjectTask {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
//  @Column(name = "id")
  private Long id;
//  @Column(name = "task_name")
  private String taskName;
//  @Column(name = "task_description")
  private String taskDescription;
//  @Column(name = "status")
  private String status;
  @NotNull
//  @Column(name = "project_name")
  private String projectName;
  @NotNull
//  @Column(name = "owner_name")
  private String ownerName;

  @ManyToOne
//  @JoinColumn(name = "project_id",nullable = false)
  @JoinColumn(name = "projectId",nullable = false)
//  @Column(name = "project")
  @JsonIgnore
  private Project project;

  @JsonFormat(pattern = "yyyy-mm--dd")
//  @Column(name = "start_date")
  private Date startDate;
  @JsonFormat(pattern = "yyyy-mm--dd")
//  @Column(name = "end_date")
  private Date endDate;
  @JsonFormat(pattern = "yyyy-mm--dd")
//  @Column(name = "create_at")
  private Date createdAt;
  @JsonFormat(pattern = "yyyy-mm--dd")
//  @Column(name = "update_at")
  private Date updateAt;
  //  private String priority;



  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public String getTaskName() {
    return taskName;
  }

  public void setTaskName(String taskName) {
    this.taskName = taskName;
  }

  public String getProjectName() {
    return projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public String getOwnerName() {
    return ownerName;
  }

  public void setOwnerName(String ownerName) {
    this.ownerName = ownerName;
  }

  public ProjectTask() {
  }

  @PrePersist
  protected void onCreated(){
    this.createdAt = new Date();
  }

  @PreUpdate
  protected void onUpdated(){
    this.createdAt = new Date();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }



  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getTaskDescription() {
    return taskDescription;
  }

  public void setTaskDescription(String taskDescription) {
    this.taskDescription = taskDescription;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdateAt() {
    return updateAt;
  }

  public void setUpdateAt(Date updateAt) {
    this.updateAt = updateAt;
  }
}
