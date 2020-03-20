package com.onboarding.firas.config;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableAutoConfiguration
public class WebApplicationConfig implements WebMvcConfigurer {

  private static final Logger logger = LogManager.getLogger(WebApplicationConfig.class);

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    if (logger.isInfoEnabled()) {
      logger.info("add swagger resource handlers ");
    }
    registry.addResourceHandler("swagger-ui.html")
        .addResourceLocations("classpath:/META-INF/resources/");
    registry.addResourceHandler("/webjars/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/");
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**");
  }

  @Bean
  public ReindexClass templateReindexer(EntityManagerFactory entityManagerFactory) {
    FullTextEntityManager manager =
        Search.getFullTextEntityManager(entityManagerFactory.createEntityManager());
    return new ReindexClass(manager);
  }
}
