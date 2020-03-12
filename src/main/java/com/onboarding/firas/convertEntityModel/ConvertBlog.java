package com.onboarding.firas.convertEntityModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.onboarding.firas.accessingdatajpa.AuthorService;
import com.onboarding.firas.accessingdatajpa.BlogEntity;
import com.onboarding.firas.authors.rest.AuthorController;
import com.onboarding.firas.blogs.rest.BlogController;
import com.onboarding.firas.common.rest.controller.BaseController;
import com.onboarding.firas.generated.model.AuthorLinks;
import com.onboarding.firas.generated.model.AuthorListLinks;
import com.onboarding.firas.generated.model.Blog;
import com.onboarding.firas.generated.model.BlogList;
import com.onboarding.firas.generated.model.BlogPOST;
import com.onboarding.firas.generated.model.HalLink;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConvertBlog {

  @Autowired
  private AuthorService authorService;

  public Blog convertEntityToModel(BlogEntity entityBlog){
    Blog blog =
        new Blog().id(entityBlog.getId())
        .text(entityBlog.getTitle())
        .title(entityBlog.getTitle())
        .author(entityBlog.getAuthor().getId().toString());
    blog.links(new AuthorLinks().self(getBlogLinkStatic(entityBlog.getId())));

    return blog;

  }
  public BlogEntity convertModelToEntity(BlogPOST modelBlog){

    BlogEntity blog = new BlogEntity(modelBlog.getTitle(), modelBlog.getText(),
        authorService.findById(Long.parseLong(modelBlog.getAuthor())) );
    return blog;
  }

  public BlogList convertEntityListToModelList(List<BlogEntity> entityList){
    BlogList blogList = new  BlogList();
    for(BlogEntity blogEntity:entityList){
      blogList.addEmbeddedItem(this.convertEntityToModel(blogEntity));
    }

    blogList.links(new AuthorListLinks()
        .self(BaseController.getHalGetLink(methodOn(BlogController.class).getBlogs(null, null))));
    return blogList;
  }

  public static HalLink getBlogLinkStatic(Long id) {
    return BlogController.getHalGetLink(methodOn(BlogController.class).getBlogById(id));
  }

}
