package com.onboarding.firas.accessingdatajpa;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

@Entity
@Indexed
public class BlogEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Field(name = "title")
  private String title;

  @Field(name = "text")
  private String text;

  //@ContainedIn

  @ManyToOne(targetEntity = AuthorEntity.class)
  @IndexedEmbedded(indexNullAs = "author",includeEmbeddedObjectId = true, targetElement = AuthorEntity.class)
  private AuthorEntity author;

  protected BlogEntity() {}

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
//  @IndexedEmbedded(/*includePaths = "author",*/ prefix = "author", targetElement = AuthorEntity.class)
  public AuthorEntity getAuthor() {
    return author;
  }

  public void setAuthor(AuthorEntity author) {
    this.author = author;
  }
}
