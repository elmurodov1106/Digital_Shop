package com.example.digital_shop.config;

import com.example.digital_shop.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
  @ExceptionHandler(value = DataNotFoundException.class)
  public ResponseEntity<String> dataNotFoundExceptionHandler(
          DataNotFoundException e
  ){
      return ResponseEntity.status(404).body(e.getMessage());
  }

  @ExceptionHandler(value = RequestValidationException.class)
    public ResponseEntity<String> requestValidationException(
            RequestValidationException e
  ){
      return ResponseEntity.status(400).body(e.getMessage());
  }

    @ExceptionHandler(value = AuthenticationFailedException.class)
    public ResponseEntity<String> authenticationFailedException(
            AuthenticationFailedException e
    )
    {
        return ResponseEntity.status(401).body(e.getMessage());
    }

  @ExceptionHandler(value = {UniqueObjectException.class})
  public ResponseEntity<String> uniqueObjectException(
          UniqueObjectException e){
    return ResponseEntity.status(400).body(e.getMessage());
  }
  @ExceptionHandler(value = {ObjectsNotFoundException.class})
  public ResponseEntity<String> objectNotFoundExceptionHandler(
          ObjectsNotFoundException e){
    return ResponseEntity.status(401).body(e.getMessage());
  }

  @ExceptionHandler(value = {UserBadRequestException.class})
  public ResponseEntity<String> userBadRequestException(
          UserBadRequestException e){
    return ResponseEntity.status(400).body(e.getMessage());
  }
  @ExceptionHandler(value = {ConflictException.class})
  public ResponseEntity<String> conflictException(ConflictException e){
    return ResponseEntity.status(409).body(e.getMessage());
  }
}

