package pl.akademiaspecjalistowit.jarfactory.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.akademiaspecjalistowit.jarfactory.exception.JarFactoryException;

@ControllerAdvice
public class JarFactoryControllerAdvice {
    @ExceptionHandler(JarFactoryException.class)
    public ResponseEntity<ErrorResponse> handleJarFactoryException(JarFactoryException e) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ErrorResponse(e.getMessage(), ErrorCode.JAR_LIMIT_EXCEEDED));
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleValidationExceptions(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ex.getMessage());
    }
}
