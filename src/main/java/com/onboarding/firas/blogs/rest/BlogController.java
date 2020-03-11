package com.onboarding.firas.blogs.rest;

import com.onboarding.firas.accessingdatajpa.BlogService;
import com.onboarding.firas.common.rest.controller.BaseController;
import com.onboarding.firas.convertEntityModel.ConvertBlog;
import com.onboarding.firas.generated.BlogsApi;
import com.onboarding.firas.generated.model.Blog;
import com.onboarding.firas.generated.model.BlogList;
import io.swagger.annotations.ApiParam;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BlogController extends BaseController implements BlogsApi {

  @Autowired
  private BlogService blogService;

  @Autowired
  private ConvertBlog convertBlog;

  @Override
  public ResponseEntity<BlogList> getBlogs(
      @ApiParam(value = "number of returned results", defaultValue = "10")
      @Valid @RequestParam(value = "limit", required = false, defaultValue="10") Integer limit,

      @ApiParam(value = "offset of returned results", defaultValue = "0")
      @Valid @RequestParam(value = "offset", required = false, defaultValue="0") Integer offset){

    try{
      BlogList blogList = this.convertBlog.convertEntityListToModelList(this.blogService.findAll());
      return  new ResponseEntity<>(blogList, HttpStatus.OK);
    }catch(Exception e)
    {
      throw e;
    }
  }

  @Override
  public ResponseEntity<Blog> getBlogById(
      @ApiParam(value = "id of blog",required=true, defaultValue="0") @PathVariable("id") Long id){
    try{
      Blog blog = this.convertBlog.convertEntityToModel(this.blogService.findById(id));
      return new ResponseEntity<>(blog, HttpStatus.OK);
    }catch(Exception e){
      throw e;
    }
  }



  @Override
  public ResponseEntity<Void> deleteBlogById(@ApiParam(value = "id of blog",required=true) @PathVariable("id") Long id){
    try{
      this.blogService.deleteBlog(id);
      //return new ResponseEntity<>(author, HttpStatus.OK);
      return new ResponseEntity<>(null , HttpStatus.OK);
    }catch(Exception e){
      //throw new IllegalArgumentException("Could not find author");
      return new ResponseEntity<>(null , HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public ResponseEntity<Blog> addBlog(@ApiParam(value = "Blog to add. Cannot null or empty." ,required=true )  @Valid @RequestBody Blog blog){
    try{
      this.blogService.addBlog(this.convertBlog.convertModelToEntity(blog));
      return new ResponseEntity<>(blog, HttpStatus.OK);
    }catch(Exception e){
      return new ResponseEntity<>(blog, HttpStatus.CONFLICT);
    }
  }

}
