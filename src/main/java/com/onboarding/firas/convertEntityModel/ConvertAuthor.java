package com.onboarding.firas.convertEntityModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.onboarding.firas.accessingdatajpa.AuthorEntity;
import com.onboarding.firas.authors.rest.AuthorController;
import com.onboarding.firas.common.rest.controller.BaseController;
import com.onboarding.firas.generated.model.Author;
import com.onboarding.firas.generated.model.AuthorLinks;
import com.onboarding.firas.generated.model.AuthorList;
import com.onboarding.firas.generated.model.AuthorListLinks;
import com.onboarding.firas.generated.model.HalLink;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ConvertAuthor {

  public Author convertEntityToModel(AuthorEntity entityAuthor){
    Author author = new Author().id(entityAuthor.getId()).
        firstname(entityAuthor.getFirstName()).
        lastname(entityAuthor.getLastName());
    author.links(new AuthorLinks().self(AuthorController.getAuthorLinkStatic(entityAuthor.getId())));
    return author;
  }
  public AuthorEntity convertModelToEntity(Author modelAuthor){
    AuthorEntity author =
        new AuthorEntity(modelAuthor.getFirstname(), modelAuthor.getLastname());
    return author;
  }

  public AuthorList convertEntityListToModelList(
      List<AuthorEntity> entityList){
    AuthorList list = new AuthorList();
    for (AuthorEntity author:entityList){
      list.addEmbeddedItem(this.convertEntityToModel( author));
    }
    list.links(new AuthorListLinks()
        .self(BaseController.getHalGetLink(methodOn(new AuthorController().getClass()).getAuthors(null, null))));
    return list;
  }


}
