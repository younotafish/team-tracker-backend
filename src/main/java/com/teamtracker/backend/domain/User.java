package com.teamtracker.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "user_name")
  private String userName; //从微信可以获取哪些？


////  @ManyToMany(mappedBy = "partners",cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
//  private List<Project> projectsInvolvedIn;
  @Column(name = "create_at")
  private Date createdAt;
  @Column(name = "update_at")
  private Date updatedAt;

  @OneToMany(fetch = FetchType.LAZY,mappedBy = "owner")
  private List<Project> projectOwned;

  @ManyToMany()
  @JoinTable(
      name="user_project",
      joinColumns = @JoinColumn(name="user_id"),
      inverseJoinColumns = @JoinColumn(name = "project_id")
  )

  @Column(name = "project_participated")
  @JsonIgnore
  private List<Project> projectParticipated;

  public List<Project> getProjectOwned() {
    return projectOwned;
  }

  public void setProjectOwned(List<Project> projectOwned) {
    this.projectOwned = projectOwned;
  }

  public List<Project> getProjectParticipated() {
    return projectParticipated;
  }

  public void setProjectParticipated(
      List<Project> projectParticipated) {
    this.projectParticipated = projectParticipated;
  }

  public User() {
  }

  public User(String userName) {
    this.userName = userName;
  }

  @PrePersist
  protected void onCreate() {
    this.createdAt = new Date();
  }

  @PreUpdate
  protected void onUpdate() {
    this.updatedAt = new Date();
  }


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }


  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

//  public List<Project> getProjectList() {
//    return projectList;
//  }
//
//  public void setProjectList(List<Project> projectList) {
//    this.projectList = projectList;
//  }
}
