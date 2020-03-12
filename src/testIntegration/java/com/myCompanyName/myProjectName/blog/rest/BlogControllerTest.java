package com.onboarding.firas.blog.rest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.onboarding.firas.ApiMatchers;
import com.onboarding.firas.WebMvcTest;
import com.onboarding.firas.generated.model.Author;
import com.onboarding.firas.generated.model.AuthorList;
import com.onboarding.firas.generated.model.Blog;
import com.onboarding.firas.generated.model.BlogList;
import com.onboarding.firas.generated.model.Error;
import org.junit.jupiter.api.Test;

public class BlogControllerTest extends WebMvcTest {
  private static final String URL = "/blogs/";

  @Test
  public void WHEN_get_blogs_THEN_response_contains_correct_data_structure() throws Exception {
    // Act
    performGET(URL)

        // Assert
        .andExpect(status().isOk())
        .andExpect(ApiMatchers.responseMatchesModel(BlogList.class));
  }

  @Test
  public void GIVEN_id_WHEN_get_blog_by_id_THEN_response_contains_correct_data_structure()
      throws Exception {
    // Arrange
    String id = "2";

    // Act
    performGET(URL + id)

        // Assert
        .andExpect(status().isOk())
        .andExpect(ApiMatchers.responseMatchesModel(Blog.class));
  }

  @Test
  public void GIVEN_not_existing_id_WHEN_get_blog_by_id_THEN_response_is_error()
      throws Exception {
    // Arrange
    String id = "0";

    // Act
    performGET(URL + id)

        // Assert
        .andExpect(status().isInternalServerError())
        .andExpect(ApiMatchers.responseMatchesModel(Error.class));
  }

  // tests implemented to test JPA an REST API integration

  @Test
  public void GIVEN_bad_json_schema_WHEN_POST_blog_THEN_response_is_error()
      throws Exception {

    // Act
    performPOST(URL, "{\n"
        + "  \"title\": \"string\",\n"
        + "  \"text\": \"string\",\n"
        + "}")

        // Assert
        .andExpect(status().isInternalServerError())
        .andExpect(ApiMatchers.responseMatchesModel(Error.class));
  }

  @Test
  public void ADD_newBlog_Using_POST_THEN_response_is_ok()
      throws Exception {

    // Act
    performPOST(URL,"{\n"
        + "  \"title\": \"test title\",\n"
        + "  \"text\": \"bla bla\",\n"
        + "  \"author\": \"1\"\n"
        + "}")

        // Assert
        .andExpect(status().isOk())
        .andExpect(ApiMatchers.responseMatchesModel(Blog.class));
  }

  @Test
  public void Delete_Blog_Using_DELETE_THEN_response_is_ok()
      throws Exception {

    // Act
    String postResult = performPOST(URL,"{\n"
        + "  \"title\": \"string\",\n"
        + "  \"text\": \"string\",\n"
        + "  \"author\": \"1\"\n"
        + "}")

        // Assert
        .andExpect(status().isOk())
        .andExpect(ApiMatchers.responseMatchesModel(Blog.class))
        .andReturn().getResponse().getContentAsString();

    Long id = Long.parseLong(postResult.split(",")[0].split(":")[1]);
    performDELETE(URL + id)

        // Assert
        .andExpect(status().isOk());
  }

  @Test
  public void EDIT_Blog_Using_PUT_THEN_response_is_ok()
      throws Exception {

    // Act
    String postResult = performPOST(URL,"{\n"
        + "  \"title\": \"string\",\n"
        + "  \"text\": \"string\",\n"
        + "  \"author\": \"1\"\n"
        + "}")

        // Assert
        .andExpect(status().isOk())
        .andExpect(ApiMatchers.responseMatchesModel(Blog.class))
        .andReturn().getResponse().getContentAsString();

    Long id = Long.parseLong(postResult.split(",")[0].split(":")[1]);

    String putResult = performPUT(URL +id,"{\n"
        + "  \"title\": \"sstring\",\n"
        + "  \"text\": \"string\",\n"
        + "  \"author\": \"1\"\n"
        + "}")

        // Assert
        .andExpect(status().isOk())
        .andExpect(ApiMatchers.responseMatchesModel(Blog.class))
        .andReturn().getResponse().getContentAsString();

    String title = putResult.split(",")[1].split(":")[1];
    /*log.info(postResult);
    log.info(putResult);

    log.info(firstName);
    log.info("ffiras");
    log.info(""+);*/

    if (!(title.compareTo("\"sstring\"")==0)){
      throw new Exception();
    }

  }
}
