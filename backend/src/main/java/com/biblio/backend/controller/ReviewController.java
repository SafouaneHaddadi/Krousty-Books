package com.biblio.backend.controller;
import com.biblio.backend.dto.ReviewRequest;
import com.biblio.backend.model.Review;
import com.biblio.backend.model.User;
import com.biblio.backend.repository.BookRepository;
import com.biblio.backend.repository.ReviewRepository;
import com.biblio.backend.repository.UserRepository;
import com.biblio.backend.service;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = {"http://localhost:4200/", "http://localhost:8082"})
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewService reviewService;


    @PostMapping
    //? = wildcard = "n’importe quel type").Ça permet de retourner soit un Review, soit une String, sans erreur de compilation
    public ResponseEntity<?> addReview(@RequestBody ReviewRequest request, Principal principal) { //Principal = interface Java que Spring Security utilise pour représenter l'user actuellement connecté

        //on vérifie que l'user est connecté
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utilisateur non authentifié");
        }

        Review savedReview = reviewService.addReview(
            request.getBookId(),
            request.getRating(),
            request.getComment(),
            principal.getName()  // username de l'utilisateur connecté
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(savedReview);
    }
}