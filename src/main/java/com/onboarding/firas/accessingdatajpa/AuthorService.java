package com.onboarding.firas.accessingdatajpa;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
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

  public AuthorEntity addAuthor(AuthorEntity author){
    return this.authorRepository.save(author);
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

  public AuthorEntity editAuthor(Long id, AuthorEntity authorEntity){
    AuthorEntity newAuthor = this.findById(id);
    newAuthor.setFirstName(authorEntity.getFirstName());
    newAuthor.setLastName(authorEntity.getLastName());
    if (!authorEntity.getBlogs().isEmpty()){
      newAuthor.setBlogs(authorEntity.getBlogs());
    }
    return this.authorRepository.save(newAuthor);

  }




}

