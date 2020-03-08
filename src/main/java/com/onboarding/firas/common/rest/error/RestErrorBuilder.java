package com.onboarding.firas.common.rest.error;

import com.onboarding.firas.generated.model.Error;
import java.util.UUID;

public class RestErrorBuilder {

  public Error buildError(Exception e, RestErrorCode errorCode) {
    return new Error()
        .id(UUID.randomUUID().toString())
        .code(errorCode.getErrorCode())
        .message(e.getMessage());
  }

}
