package com.onboarding.firas.config;

import lombok.RequiredArgsConstructor;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

public class ReindexClass implements InitializingBean {

  private FullTextEntityManager fullTextEntityManager;

  public ReindexClass(FullTextEntityManager fullTextEntityManager) {
    this.fullTextEntityManager = fullTextEntityManager;
  }
  @Override
  public void afterPropertiesSet() throws Exception {
    this.fullTextEntityManager.createIndexer().startAndWait();
  }
}
