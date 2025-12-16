package com.biblio.backend.service;

import com.biblio.backend.model.Book;
import com.biblio.backend.model.Review;
import com.biblio.backend.model.User;
import com.biblio.backend.repository.BookRepository;
import com.biblio.backend.repository.ReviewRepository;
import com.biblio.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;

    public Review addReview(Long bookId, int rating, String comment, String username) {

        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("La note doit être comprise entre 1 et 5.");
        }

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Livre introuvable avec l'ID " + bookId));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        Review review = new Review(rating, comment, book, user);

        return reviewRepository.save(review);
    }
}

