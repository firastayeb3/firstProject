package com.onboarding.firas.authors.rest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.onboarding.firas.ApiMatchers;
import com.onboarding.firas.Application;
import com.onboarding.firas.WebMvcTest;
import com.onboarding.firas.generated.model.Author;
import com.onboarding.firas.generated.model.AuthorList;
import com.onboarding.firas.generated.model.Error;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthorControllerTest extends WebMvcTest {

  private static final String URL = "/authors/";
  private static final Logger log = LoggerFactory.getLogger(Application.class);

  @Test
  public void WHEN_get_authors_THEN_response_contains_correct_data_structure() throws Exception {
    // Act
    performGET(URL)

        // Assert
        .andExpect(status().isOk())
        .andExpect(ApiMatchers.responseMatchesModel(AuthorList.class));
  }

  @Test
  public void GIVEN_id_WHEN_get_author_by_id_THEN_response_contains_correct_data_structure()
      throws Exception {
    // Arrange
    String id = "1";

    // Act
    performGET(URL + id)

        // Assert
        .andExpect(status().isOk())
        .andExpect(ApiMatchers.responseMatchesModel(Author.class));
  }

  @Test
  public void GIVEN_not_existing_id_WHEN_get_author_by_id_THEN_response_is_error()
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
  public void GIVEN_bad_json_schema_WHEN_POST_author_THEN_response_is_error()
      throws Exception {

    // Act
    performPOST(URL,"{\n"
        + "  \"id\": \"0\",\n"
        + "  \"firstname\": \"firas\",\n"
        + "  \"_links\": {\n"
        + "    \"self\": {\n"
        + "      \"href\": \"string\",\n"
        + "      \"method\": \"GET\",\n"
        + "      \"templated\": true\n"
        + "    }\n"
        + "  }\n"
        + "}")

        // Assert
        .andExpect(status().isInternalServerError())
        .andExpect(ApiMatchers.responseMatchesModel(Error.class));
  }

  @Test
  public void ADD_newAuthor_Using_POST_THEN_response_is_ok()
      throws Exception {

    // Act
    performPOST(URL,"{\n"
        + "  \"firstname\": \"firas\",\n"
        + "  \"lastname\": \"tayeb\"\n"
        + "}")

        // Assert
        .andExpect(status().isOk())
        .andExpect(ApiMatchers.responseMatchesModel(Author.class));
  }

  @Test
  public void Delete_Author_Using_DELETE_THEN_response_is_ok()
      throws Exception {

    // Act
    String postResult = performPOST(URL,"{\n"
        + "  \"firstname\": \"firas\",\n"
        + "  \"lastname\": \"tayeb\"\n"
        + "}")

        // Assert
        .andExpect(status().isOk())
        .andExpect(ApiMatchers.responseMatchesModel(Author.class))
        .andReturn().getResponse().getContentAsString();

    Long id = Long.parseLong(postResult.split(",")[0].split(":")[1]);
    performDELETE(URL + id)

        // Assert
        .andExpect(status().isOk());
  }

  @Test
  public void EDIT_Author_Using_PUT_THEN_response_is_ok()
      throws Exception {

    // Act
    String postResult = performPOST(URL,"{\n"
        + "  \"firstname\": \"firas\",\n"
        + "  \"lastname\": \"tayeb\"\n"
        + "}")

        // Assert
        .andExpect(status().isOk())
        .andExpect(ApiMatchers.responseMatchesModel(Author.class))
        .andReturn().getResponse().getContentAsString();

    Long id = Long.parseLong(postResult.split(",")[0].split(":")[1]);

    String putResult = performPUT(URL +id,"{\n"
        + "  \"firstname\": \"ffiras\",\n"
        + "  \"lastname\": \"tayeb\"\n"
        + "}")

        // Assert
        .andExpect(status().isOk())
        .andExpect(ApiMatchers.responseMatchesModel(Author.class))
        .andReturn().getResponse().getContentAsString();

    String firstName = putResult.split(",")[1].split(":")[1];
    /*log.info(postResult);
    log.info(putResult);

    log.info(firstName);
    log.info("ffiras");
    log.info(""+);*/

    if (!(firstName.compareTo("\"ffiras\"")==0)){
      throw new Exception();
    }

  }





}
