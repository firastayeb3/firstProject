package com.onboarding.firas.convertEntityModel;

import com.onboarding.firas.accessingdatajpa.AuthorService;
import com.onboarding.firas.accessingdatajpa.BlogEntity;
import com.onboarding.firas.generated.model.Blog;
import com.onboarding.firas.generated.model.BlogList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ConvertBlog {
  private AuthorService authorService;

  public Blog convertEntityToModel(BlogEntity entityBlog){
    Blog blog =
        new Blog().id(entityBlog.getId())
        .text(entityBlog.getTitle())
        .title(entityBlog.getTitle())
        .author(entityBlog.getAuthor().getId().toString());

    return blog;

  }
  public BlogEntity convertModelToEntity(Blog modelBlog){

    BlogEntity blog = new BlogEntity(modelBlog.getTitle(), modelBlog.getText(),
        authorService.findById(Long.getLong(modelBlog.getAuthor())) );
    return blog;
  }

  public BlogList convertEntityListToModelList(List<BlogEntity> entityList){
    BlogList blogList = new  BlogList();
    for(BlogEntity blogEntity:entityList){
      blogList.addEmbeddedItem(this.convertEntityToModel(blogEntity));
    }
    return blogList;
  }
}
