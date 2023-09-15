package com.spotify.app.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleSecurityException(Exception ex) {
        ProblemDetail errorDetail = null ;
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

    @ExceptionHandler({UserException.class})
    public ResponseEntity<Object> handleUserException(
            UserException ex) {

        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        ApiError err = new ApiError(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND,
                "An error occur in User service" ,
                details);

        return ResponseEntityBuilder.build(err);
    }
}
