package com.onboarding.firas.accessingdatajpa;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
//@RequiredArgsConstructor
public class AuthorService  {

  //@Autowired
  private final AuthorRepository authorRepository;


  /*@Autowired*/
  public AuthorService(
      AuthorRepository authorRepository) {
    this.authorRepository = authorRepository;
  }

  public List<AuthorEntity> findAll(){
    return StreamSupport
        .stream(this.authorRepository.findAll().spliterator(), false)
        .collect(Collectors.toList());

  }

  public void addAuthor(AuthorEntity author){
    this.authorRepository.save(author);
  }

  public AuthorEntity findById(Long id){
    return this.authorRepository.findById(id).get();
  }

  public void deleteAuthor(AuthorEntity authorEntity){
    this.authorRepository.delete(authorEntity);
  }
  public void deleteAuthorById(Long id){
    this.authorRepository.deleteById(id);
  }

  public void editAuthor(AuthorEntity authorEntity){
    this.authorRepository.save(authorEntity);

  }



}

