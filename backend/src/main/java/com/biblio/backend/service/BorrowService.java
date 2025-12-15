package com.biblio.backend.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biblio.backend.model.Book;
import com.biblio.backend.model.Borrow;
import com.biblio.backend.model.User;
import com.biblio.backend.repository.BookRepository;
import com.biblio.backend.repository.BorrowRepository;
import com.biblio.backend.repository.UserRepository;

@Service
public class BorrowService {

    private final BorrowRepository borrowRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public BorrowService(BorrowRepository borrowRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.borrowRepository = borrowRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    // Récupérer les emprunts de l'utilisateur connecté
    public List<Borrow> getMyBorrows(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        return borrowRepository.findByBorrower(user);
    }

    @Transactional // Important car on modifie deux tables (Borrow et Book)
    public Borrow createBorrow(Long bookId, String username) {
        // 1. Récupérer le livre
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Livre introuvable"));

        // 2. Vérifier le stock
        if (book.getStock() <= 0) {
            throw new RuntimeException("Livre indisponible (stock épuisé)");
        }

        // 3. Récupérer l'utilisateur
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        // 4. Créer l'emprunt
        Borrow borrow = new Borrow();
        borrow.setBook(book);
        borrow.setBorrower(user);
        borrow.setBorrowDate(LocalDate.now());
        
        // 5. Décrémenter le stock et sauvegarder
        book.setStock(book.getStock() - 1);
        bookRepository.save(book);

        return borrowRepository.save(borrow);
    }
    
    @Transactional
    public void returnBook(Long borrowId) {
        Borrow borrow = borrowRepository.findById(borrowId)
                .orElseThrow(() -> new RuntimeException("Emprunt introuvable"));

        if (borrow.getReturnDate() != null) {
            throw new RuntimeException("Livre déjà rendu !");
        }

        // Marquer comme rendu
        borrow.setReturnDate(LocalDate.now());
        
        // Remettre le stock (+1)
        Book book = borrow.getBook();
        book.setStock(book.getStock() + 1);
        
        bookRepository.save(book);
        borrowRepository.save(borrow);
    }
}