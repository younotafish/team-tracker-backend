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
  private Long id;
//  private UUID uuid;
  private String taskName;

  private String taskDescription;
  private String status;
  @NotNull
  private String projectName;
  @NotNull
  private String ownerName;

  @ManyToOne
  @JoinColumn(name = "project_id",nullable = false)
  @JsonIgnore
  private Project project;

  @JsonFormat(pattern = "yyyy-mm--dd")
  private Date startDate;
  @JsonFormat(pattern = "yyyy-mm--dd")
  private Date endDate;
  @JsonFormat(pattern = "yyyy-mm--dd")
  private Date createdAt;
  @JsonFormat(pattern = "yyyy-mm--dd")
  private Date updateAt;




  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

//  public UUID getUuid() {
//    return uuid;
//  }
//
//
//  public void setUuid(UUID uuid) {
//    this.uuid = uuid;
//  }

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

//  private String priority;

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
