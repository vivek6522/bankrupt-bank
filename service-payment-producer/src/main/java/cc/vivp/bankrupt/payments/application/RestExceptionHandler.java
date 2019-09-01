package cc.vivp.bankrupt.payments.application;

import javax.persistence.EntityNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import cc.vivp.bankrupt.error.models.ApiError;
import cc.vivp.bankrupt.payments.models.api.PaymentDispatchException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "Malformed JSON request", ex));
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "Invalid input", ex));
  }

  @ExceptionHandler(PaymentDispatchException.class)
  protected ResponseEntity<Object> handlePaymentDispatchException(PaymentDispatchException ex) {
    return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Technical error occured while dispatching payment", ex));
  }

  @ExceptionHandler(EntityNotFoundException.class)
  protected ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
    return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, "Not found", ex));
  }

  private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
    return new ResponseEntity<>(apiError, apiError.getStatus());
  }

}
