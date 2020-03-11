package com.onboarding.firas.accessingdatajpa;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//@RepositoryRestResource(collectionResourceRel = "authors", path = "authors")
public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {

  //List<AuthorEntity> findAll();
  List<AuthorEntity> findByFirstName(@Param("name") String name);


}
/*
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<AuthorEntity, Long>{

}
 */