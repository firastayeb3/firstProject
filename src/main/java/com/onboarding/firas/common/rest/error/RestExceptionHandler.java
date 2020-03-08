package com.onboarding.firas.common.rest.error;

import static com.onboarding.firas.common.rest.error.RestErrorCode.*;

import com.onboarding.firas.generated.model.Error;
import javax.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@ControllerAdvice
public class RestExceptionHandler {

  private RestErrorBuilder restErrorBuilder = new RestErrorBuilder();
  private RestErrorLogger restErrorLogger = new RestErrorLogger();

  @ExceptionHandler({Exception.class})
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public Error exception(final Exception e) {
    Error error = restErrorBuilder.buildError(e, SERVER_ERROR);
    restErrorLogger.log(e, error);
    return error;
  }

  @ExceptionHandler({ValidationException.class})
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ResponseBody
  public Error validationException(final Exception e) {
    Error error = restErrorBuilder.buildError(e, VALIDATION_EXCEPTION);
    restErrorLogger.log(e, error);
    return error;
  }

  @ExceptionHandler({HttpClientErrorException.NotFound.class})
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  @ResponseBody
  public Error notFoundException(final Exception e) {
    Error error = restErrorBuilder.buildError(e, NOT_FOUND_ERROR);
    restErrorLogger.log(e, error);
    return error;
  }

  @ExceptionHandler({HttpClientErrorException.Forbidden.class})
  @ResponseStatus(value = HttpStatus.FORBIDDEN)
  @ResponseBody
  public Error forbiddenException(final Exception e) {
    Error error = restErrorBuilder.buildError(e, FORBIDDEN);
    restErrorLogger.log(e, error);
    return error;
  }

  @ExceptionHandler({HttpServerErrorException.InternalServerError.class})
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public Error internalServerErrorException(final Exception e) {
    Error error = restErrorBuilder.buildError(e, INTERNAL_SERVER_ERROR);
    restErrorLogger.log(e, error);
    return error;
  }

  // TODO add your own exception handlers

}
