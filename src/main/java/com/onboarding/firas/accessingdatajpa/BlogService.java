package com.onboarding.firas.accessingdatajpa;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service

@RequiredArgsConstructor
public class BlogService {
  private final BlogRepository blogRepository;


  @PersistenceContext
  private EntityManager em;

  public BlogService(
      BlogRepository blogRepository) {
    this.blogRepository = blogRepository;
  }

  public List<BlogEntity> findAll(){
    return StreamSupport
        .stream(this.blogRepository.findAll().spliterator(), false)
        .collect(Collectors.toList());

  }

  public BlogEntity addBlog(BlogEntity blog){
    return this.blogRepository.save(blog);
  }

  public BlogEntity findById(Long id){
    return this.blogRepository.findById(id).get();
  }

  public void deleteBlog(Long id){
    this.blogRepository.deleteById(id);
  }

  public BlogEntity editBlog(Long id, BlogEntity newBlog){
    BlogEntity dbBlog = this.blogRepository.findById(id).get();
    dbBlog.setText(newBlog.getText());
    dbBlog.setTitle(newBlog.getTitle());
    return this.blogRepository.save(newBlog);
  }


  public List <BlogEntity> findBlog(String stringToFind) throws InterruptedException {
    //FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(em);
    FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);
    //em.getTransaction().begin();
    try{

      QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(BlogEntity.class).get();

      org.apache.lucene.search.Query query = qb
          .keyword()
          .onFields("author.firstName",
              "author.lastName",
              //"id"
             "text",
              "title" )
          .matching(stringToFind)
          .createQuery();

      // wrap Lucene query in a javax.persistence.Query
      javax.persistence.Query persistenceQuery = fullTextEntityManager.createFullTextQuery(query,BlogEntity.class);
      // execute search
      List result = persistenceQuery.getResultList();
      //em.getTransaction().commit();
      em.close();

      return result;
    }catch (Exception e){
      throw e;
      //return  null;
    }

  }
}
