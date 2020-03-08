package com.onboarding.firas.common.rest.error;

public enum RestErrorCode {

  SERVER_ERROR("2000"),
  VALIDATION_EXCEPTION("2001"),
  NOT_FOUND_ERROR("404"),
  FORBIDDEN("403"),
  INTERNAL_SERVER_ERROR("500")
  // TODO add your own error codes
  ;

  private String errorCode;

  RestErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  public String getErrorCode() {
    return errorCode;
  }
}
