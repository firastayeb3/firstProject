package com.onboarding.firas.accessingdatajpa;

import com.onboarding.firas.Application;
import com.onboarding.firas.authors.rest.AuthorController;
import com.onboarding.firas.convertEntityModel.ConvertAuthor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

  @EventListener(ApplicationReadyEvent.class)
  public void initData() {
    AuthorEntity author = new AuthorEntity("Max", "Mustermann");
    BlogEntity blog = new BlogEntity("first blog", "this is just a test text", author);

    this.authorService.addAuthor(author);
    this.blogService.addBlog(blog);
    //this.authorService.addAuthor(this.convertAuthor.convertModelToEntity(author2));

    log.info("************** All authors ****************** \n");
    //log.info((new AuthorController()).getAuthors(null, null).toString());
  }


}
