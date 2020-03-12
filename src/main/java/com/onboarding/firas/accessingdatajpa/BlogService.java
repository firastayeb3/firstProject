package com.onboarding.firas.accessingdatajpa;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service

@RequiredArgsConstructor
public class BlogService {
  private final BlogRepository blogRepository;


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
}
