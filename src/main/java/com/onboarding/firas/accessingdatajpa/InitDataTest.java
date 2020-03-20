package com.onboarding.firas.accessingdatajpa;

import com.onboarding.firas.Application;
import com.onboarding.firas.authors.rest.AuthorController;
import com.onboarding.firas.config.WebApplicationConfig;
import com.onboarding.firas.convertEntityModel.ConvertAuthor;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import org.springframework.context.event.EventListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class InitDataTest {

  private static final Logger log = LoggerFactory.getLogger(Application.class);

  @Autowired
  private AuthorService authorService;

  @Autowired
  private BlogService blogService;

  /*@PersistenceContext
  private EntityManager em;*/

  @EventListener(ApplicationReadyEvent.class)
  public void initData() throws InterruptedException {
    AuthorEntity author = new AuthorEntity("Max", "Mustermann");
    BlogEntity blog = new BlogEntity("first blog", "this is just a test text", author);

    this.authorService.addAuthor(author);
    this.blogService.addBlog(blog);
    //this.authorService.addAuthor(this.convertAuthor.convertModelToEntity(author2));

    //log.info("************** All authors ****************** \n");
    //log.info((new AuthorController()).getAuthors(null, null).toString());
    //em.persist(author);
    //em.persist(blog);
    // fullTextEntityManager = Search.getFullTextEntityManager(em);
    //fullTextEntityManager.createIndexer().startAndWait();

  }


}
