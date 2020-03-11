package com.onboarding.firas.authors.rest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import com.onboarding.firas.accessingdatajpa.AuthorService;
import com.onboarding.firas.common.rest.controller.BaseController;

import com.onboarding.firas.convertEntityModel.ConvertAuthor;
import com.onboarding.firas.generated.AuthorsApi;
import com.onboarding.firas.generated.model.Author;
import com.onboarding.firas.generated.model.AuthorLinks;
import com.onboarding.firas.generated.model.AuthorList;
import com.onboarding.firas.generated.model.AuthorListLinks;
import com.onboarding.firas.generated.model.Error;
import com.onboarding.firas.generated.model.HalLink;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AuthorController extends BaseController implements AuthorsApi {

  @Autowired
  private AuthorService authorService ;
  @Autowired
  private ConvertAuthor convertAuthor;

  @Override
  public ResponseEntity<AuthorList> getAuthors(
      @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
      @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {

    Author author1 = new Author().id(1L)
        .firstname("Max")
        .lastname("Mustermann")
        .links(new AuthorLinks().self(getAuthorLink(1L)));

    Author author2 = new Author().id(2L)
        .firstname("Test")
        .lastname("User")
        .links(new AuthorLinks().self(getAuthorLink(2L)));

    //convert model to entity and save to DB
    this.authorService.addAuthor(this.convertAuthor.convertModelToEntity(author1));
    this.authorService.addAuthor(this.convertAuthor.convertModelToEntity(author2));

    //get entity from DB
    //Author author11 = this.convertAuthor.convertEntityToModel(this.authorService.findAll().get(0));
    //Author author22 = this.convertAuthor.convertEntityToModel(this.authorService.findAll().get(1));

    AuthorList authorList = new AuthorList()
        .embedded(Stream.of(author1, author2).collect(Collectors.toList()))
        .links(new AuthorListLinks()
            .self(getHalGetLink(methodOn(this.getClass()).getAuthors(limit, offset))));

   // AuthorList authorList = this.convertAuthor.EntityListToModelList(this.authorService.findAll());


    try{
      //AuthorList authorList = this.convertAuthor.convertEntityListToModelList(this.authorService.findAll());
      return new ResponseEntity<>(authorList, HttpStatus.OK);
    }catch(Exception e){
      throw e;
    }

  }

  @Override
  public ResponseEntity<Author> getAuthorById(@PathVariable("id") Long id) {

    try{
      Author author = this.convertAuthor.convertEntityToModel(this.authorService.findById(id));
      return new ResponseEntity<>(author, HttpStatus.OK);
    }catch(Exception e) {
      throw e;
      //throw new IllegalArgumentException("Could not find author id = "+ Long.parseLong(id.toString()));
    }


    /*if(id == 1) {
      Author author = new Author().id("1")
          .firstname("Max")
          .lastname("Mustermann")
          .links(new AuthorLinks().self(getAuthorLink(id)));

    } else {
      throw new IllegalArgumentException("Could not find author");
    }*/
  }

  @Override
  public ResponseEntity<Author> addAuthor(@ApiParam(value = "Author to add. Cannot null or empty." ,required=true )  @Valid @RequestBody Author author){
    try{
      this.authorService.addAuthor(this.convertAuthor.convertModelToEntity(author));
      return new ResponseEntity<>(author, HttpStatus.OK);
    }catch(Exception e){
      return new ResponseEntity<>(author, HttpStatus.CONFLICT);
    }
  }

  @Override
  public ResponseEntity<Author> deleteAuthorById(@ApiParam(value = "id of author",required=true) @PathVariable("id") Long id){
    try{
      //throw new IllegalArgumentException("Could not find author"+ this.authorService.findById(Long.parseLong(id.toString())));
      //this.authorService.deleteAuthor(this.authorService.findById(Long.parseLong(id.toString())));
      this.authorService.deleteAuthorById(id);
      //return new ResponseEntity<>(author, HttpStatus.OK);
      //throw new IllegalArgumentException("Could not find author"+ Long.parseLong(id.toString()));
      return new ResponseEntity<>(null , HttpStatus.OK);
    }catch(Exception e){
      //throw new IllegalArgumentException("Could not find author");
      throw e;
      //return new ResponseEntity<>(null , HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public ResponseEntity<Void> updateAuthor(
      @ApiParam(value = "Id of the author to be update. Cannot be empty.",required=true)
      @PathVariable("id") Long id,
      @ApiParam(value = "Author to update. Cannot null or empty." ,required=true )
      @Valid @RequestBody Author author){

    try{
      author.setId(id);
      this.authorService.editAuthor(this.convertAuthor.convertModelToEntity(author));
      return new ResponseEntity<>(null , HttpStatus.OK);
    }catch(Exception e){
      throw e;
    }

  }

  public HalLink getAuthorLink(Long id) {
    return getHalGetLink(methodOn(this.getClass()).getAuthorById(id));
  }

}
