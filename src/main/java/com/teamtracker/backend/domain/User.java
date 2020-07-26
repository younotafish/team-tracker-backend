package com.teamtracker.backend.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String userName; //从微信可以获取哪些？

  private String passworld;

  private String conformPassword;

////  @ManyToMany(mappedBy = "partners",cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
//  private List<Project> projectsInvolvedIn;

  private Date createdAt;

  private Date updatedAt;

  @OneToMany(fetch = FetchType.LAZY,mappedBy = "owner")
  private List<Project> projectOwned = new ArrayList<>();

  @ManyToMany()
  @JoinTable(
      name="user_project",
      joinColumns = @JoinColumn(name="user_id"),
      inverseJoinColumns = @JoinColumn(name = "project_id")
  )
  private List<Project> projectParticipated;


  public User() {
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

  public String getPassworld() {
    return passworld;
  }

  public void setPassworld(String passworld) {
    this.passworld = passworld;
  }

  public String getConformPassword() {
    return conformPassword;
  }

  public void setConformPassword(String conformPassword) {
    this.conformPassword = conformPassword;
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
