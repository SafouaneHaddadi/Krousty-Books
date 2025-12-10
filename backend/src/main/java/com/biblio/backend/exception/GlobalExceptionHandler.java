package com.biblio.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    // 1. Erreurs de validation (@Valid dans controller)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors); // HTTP 400
    }
    
    // 2. erreurs métier de UserService
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleBusinessExceptions(
            RuntimeException ex) {
        
        Map<String, String> error = new HashMap<>();
        String message = ex.getMessage();
        error.put("error", message);
        
        // Donne le bon HTTP status selon le message
        if (message.contains("Mot de passe incorrect")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error); // 401
        } else if (message.contains("Utilisateur non trouvé")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error); // 404
        } else if (message.contains("déjà pris") || message.contains("déjà utilisé")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error); // 409
        } else {
            return ResponseEntity.badRequest().body(error); // 400 par défaut
        }
    }
}
