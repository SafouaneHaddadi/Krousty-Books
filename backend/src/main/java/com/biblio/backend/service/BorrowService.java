package com.biblio.backend.service;

import com.biblio.backend.dto.BorrowResponse;
import com.biblio.backend.model.Book;
import com.biblio.backend.model.Borrow;
import com.biblio.backend.model.User;
import com.biblio.backend.repository.BookRepository;
import com.biblio.backend.repository.BorrowRepository;
import com.biblio.backend.repository.UserRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BorrowService {

    @Autowired
    private BorrowRepository borrowRepository;
    
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private UserRepository userRepository;

    // Récupérer les emprunts de l'utilisateur connecté
    public List<BorrowResponse> getMyBorrows(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("Utilisateur introuvable");
        }
        
        List<Borrow> borrows = borrowRepository.findByBorrowerOrderByBorrowDateDesc(user);
        List<BorrowResponse> responseList = new ArrayList<>();
        
        // Conversion simple avec une boucle
        for (Borrow borrow : borrows) {
            BorrowResponse response = new BorrowResponse();
            response.setId(borrow.getId());
            response.setBookId(borrow.getBook().getId());
            response.setBookTitle(borrow.getBook().getTitle());
            response.setBorrowDate(borrow.getBorrowDate());
            response.setDueDate(borrow.getDueDate());
            response.setReturnDate(borrow.getReturnDate());
            
            // Calcul du retard
            boolean isOverdue = borrow.getReturnDate() == null 
                    && LocalDate.now().isAfter(borrow.getDueDate());
            response.setOverdue(isOverdue);
            
            responseList.add(response);
        }
        
        return responseList;
    }

    @Transactional
    public Borrow createBorrow(Long bookId, String username) {
        // 1. Récupérer le livre
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null) {
            throw new RuntimeException("Livre introuvable");
        }

        // 2. Vérifier le stock
        if (book.getStock() <= 0) {
            throw new RuntimeException("Livre indisponible (stock épuisé)");
        }

        // 3. Récupérer l'utilisateur
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("Utilisateur introuvable");
        }

        // 4. Créer l'emprunt
        Borrow borrow = new Borrow();
        borrow.setBook(book);
        borrow.setBorrower(user);
        borrow.setBorrowDate(LocalDate.now());
        borrow.setDueDate(LocalDate.now().plusDays(14)); // 14 jours par défaut
        
        // 5. Décrémenter le stock et sauvegarder
        book.setStock(book.getStock() - 1);
        bookRepository.save(book);

        return borrowRepository.save(borrow);
    }
    
    @Transactional
    public void returnBook(Long borrowId, String username) {  
        Borrow borrow = borrowRepository.findById(borrowId).orElse(null);
        if (borrow == null) {
            throw new RuntimeException("Emprunt introuvable");
        }

        User currentUser = userRepository.findByUsername(username);
        if (currentUser == null) {
            throw new RuntimeException("Utilisateur introuvable");
        }

        if (!borrow.getBorrower().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Vous ne pouvez rendre que vos propres emprunts");
        }

        if (borrow.getReturnDate() != null) {
            throw new RuntimeException("Livre déjà rendu");
        }

        // Vérification de retard
        if (LocalDate.now().isAfter(borrow.getDueDate())) {
            System.out.println("Retard détecté pour l'emprunt " + borrowId);
        }

        borrow.setReturnDate(LocalDate.now());
        Book book = borrow.getBook();
        book.setStock(book.getStock() + 1);
        bookRepository.save(book);
        borrowRepository.save(borrow);
    }
}