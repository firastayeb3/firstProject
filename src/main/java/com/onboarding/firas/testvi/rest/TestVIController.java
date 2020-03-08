package com.onboarding.firas.testvi.rest;

import com.onboarding.firas.common.rest.controller.BaseController;
import com.onboarding.firas.generated.AuthorsApi;
import com.onboarding.firas.generated.TestVIApi;
import com.onboarding.firas.generated.model.TestVI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class TestVIController extends BaseController implements TestVIApi {

  @Override
  public ResponseEntity<TestVI> getStringTest() {

    TestVI vi = new TestVI().value("Virtual Identity");

    return new ResponseEntity<>(vi, HttpStatus.OK);
  }
}
