package com.biblio.backend.controller;

import com.biblio.backend.dto.ReviewRequest;
import com.biblio.backend.model.Review;
import com.biblio.backend.service.ReviewService; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = {"http://localhost:4200/", "http://localhost:8082"})
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<?> addReview(@RequestBody ReviewRequest request, Principal principal) {

        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utilisateur non authentifié");
        }

        try {
            Review savedReview = reviewService.addReview(
                request.getBookId(),
                request.getRating(),
                request.getComment(),
                principal.getName()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(savedReview);

        } catch (RuntimeException e) {
    
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}