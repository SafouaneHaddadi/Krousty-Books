package com.biblio.backend.controller;
import com.biblio.backend.dto.ReviewRequest;
import com.biblio.backend.model.Book;
import com.biblio.backend.model.Review;
import com.biblio.backend.model.User;
import com.biblio.backend.repository.BookRepository;
import com.biblio.backend.repository.ReviewRepository;
import com.biblio.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8082"})
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;
   
    @PostMapping
    public ResponseEntity<?> addReview(@RequestBody ReviewRequest request) {
       
        if (request.getRating() < 1 || request.getRating() > 5) {
            return ResponseEntity.badRequest().body("La note doit être comprise entre 1 et 5.");
        }

        Optional<Book> bookOpt = bookRepository.findById(request.getBookId());
        if (bookOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livre introuvable avec l'ID " + request.getBookId());
        }
        Optional<User> userOpt = userRepository.findById(request.getUserId());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur introuvable avec l'ID " + request.getUserId());
        }
        Review review = new Review(
            request.getRating(),
            request.getComment(),
            bookOpt.get(),
            userOpt.get()
        );

        Review savedReview = reviewRepository.save(review);
        return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
    }
    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<Review>> getReviewsByBook(@PathVariable Long bookId) {
        List<Review> reviews = reviewRepository.findByBookId(bookId);
        return ResponseEntity.ok(reviews);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Review>> getReviewsByUser(@PathVariable Long userId) {
        List<Review> reviews = reviewRepository.findByUserId(userId);
        return ResponseEntity.ok(reviews);
    }
}