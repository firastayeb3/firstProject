//package com.myCompanyName.myProjectName.TestVI.rest;
package com.onboarding.firas.authors.rest;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import com.onboarding.firas.generated.model.TestVI;
import com.onboarding.firas.testvi.rest.TestVIController;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

public class TestVIControllerTest {

  @Test
  public void WHEN_get_test_THEN_response_is_not_null() {
    // Arrange
    TestVIController viController = new TestVIController();

    // Act
    ResponseEntity<TestVI> test = viController.getStringTest();

    // Assert
    assertThat(test, is(notNullValue()));
  }

  @Test
  public void WHEN_get_test_THEN_response_contains_vi_string() {
    // Arrange
    TestVIController viController = new TestVIController();

    // Act
    ResponseEntity<TestVI> response = viController.getStringTest();

    // Assert
    TestVI test = response.getBody();
    assertThat(test, is(notNullValue()));
    assertThat(test.getValue(), is(equalTo("Virtual Identity")));
  }

}
