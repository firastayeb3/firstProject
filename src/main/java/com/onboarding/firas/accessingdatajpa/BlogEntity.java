package com.onboarding.firas.accessingdatajpa;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class BlogEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String title;
  private String text;

  @ManyToOne
  private AuthorEntity author;

  protected BlogEntity() {
  }

  ;

  public BlogEntity(String title, String text, AuthorEntity author) {
    this.title = title;
    this.text = text;

    this.author = author;
    this.author.addBlogs(this);
  }

  @Override
  public String toString() {
    return String.format(
        "Blog[id=%d, title='%s', text='%s', author= \n '']",
        id, title, text/*, author.getFirstName() + " " + author.getLastName()*/);
  }

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }


  //@JoinColumn(name = "blogId")
  public AuthorEntity getAuthor() {
    return author;
  }

  public void setAuthor(AuthorEntity author) {
    this.author = author;
  }
}
