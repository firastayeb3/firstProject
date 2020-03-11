package com.onboarding.firas.accessingdatajpa;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class AuthorEntity {

  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Long id;
  private String firstName;
  private String lastName;

  @OneToMany(targetEntity = BlogEntity.class, mappedBy = "author", orphanRemoval = true, cascade = CascadeType.ALL,  fetch= FetchType.EAGER)
  private List<BlogEntity> blogs = new ArrayList<BlogEntity>();

  protected AuthorEntity(){};

  public AuthorEntity(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  @Override
  public String toString() {
    return String.format(
        "Author[id=%d, firstName='%s', lastName='%s']",/*, blogs= '%s'*/
        id, firstName, lastName/*, blogs.toString()*/);
  }

  public Long getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public List<BlogEntity> getBlogs() {
    return blogs;
  }

  public void setBlogs(List<BlogEntity> blogs) {
    this.blogs = blogs;
  }

  public void addBlogs(BlogEntity blog) {
    this.blogs.add(blog);
  }

}
