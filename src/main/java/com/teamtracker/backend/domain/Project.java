package com.teamtracker.backend.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import org.hibernate.mapping.ToOne;

@Entity
public class Project {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(updatable = false,unique = true)
  private String projectName;
  private String projectDescription;

  @JsonFormat(pattern = "yyyy-mm-dd")
  private Date startDate;
  @JsonFormat(pattern = "yyyy-mm-dd")
  private Date endDate;
  @JsonFormat(pattern = "yyyy-mm-dd")
  private Date createdAt;
  @JsonFormat(pattern = "yyyy-mm-dd")
  private Date updatedAt;

  @ManyToOne
  @JsonIgnore
  private User owner;

  @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
  @JoinTable(
      name = "user_project",
      joinColumns = {@JoinColumn(name="project_id")},
      inverseJoinColumns = @JoinColumn(name = "user_id")
  )
  @JsonIgnore
  private List<User> partners;

  public User getOwner() {
    return owner;
  }

  public void setOwner(User owner) {
    this.owner = owner;
  }

  public List<User> getPartners() {
    return partners;
  }

  public void setPartners(List<User> partners) {
    this.partners = partners;
  }

  public Project() {
  }

  protected void onCreated(){
    this.createdAt = new Date();
  }

  protected void onUpdate(){
    this.updatedAt = new Date();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getProjectName() {
    return projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }



  public String getProjectDescription() {
    return projectDescription;
  }

  public void setProjectDescription(String projectDescription) {
    this.projectDescription = projectDescription;
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

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }
}
