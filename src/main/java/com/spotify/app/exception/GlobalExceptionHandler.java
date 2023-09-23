package com.spotify.app.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {



    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<Object> handleException(
            ResourceNotFoundException ex) {

        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        ApiError err = new ApiError(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND,
                "An error occur in service" ,
                details);

        return ResponseEntityBuilder.build(err);
    }

    @ExceptionHandler({DuplicateResourceException.class})
    public ResponseEntity<Object> handleException(
            DuplicateResourceException ex) {

        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        ApiError err = new ApiError(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND,
                "Resource is exited" ,
                details);

        return ResponseEntityBuilder.build(err);
    }


    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        Map< String, String > errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(),error.getDefaultMessage())
        );
        return ResponseEntity.ok().body(errors);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleSecurityException(Exception ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        ApiError err = new ApiError() ;
        err.setErrors(details);
        err.setLocalDateTime(LocalDateTime.now());

        if(ex instanceof SignatureException) {
            err.setStatus(HttpStatus.valueOf(403));
            err.setMessage("JWT Signature not valid");
            return ResponseEntityBuilder.build(err) ;
        }

        if(ex instanceof ExpiredJwtException ) {
            err.setStatus(HttpStatus.valueOf(403));
            err.setMessage("JWT Signature already expired");
            return ResponseEntityBuilder.build(err) ;
        }
        err.setMessage("An error occur in server");
        err.setStatus(HttpStatus.valueOf(404));
        return ResponseEntityBuilder.build(err) ;
    }
}
