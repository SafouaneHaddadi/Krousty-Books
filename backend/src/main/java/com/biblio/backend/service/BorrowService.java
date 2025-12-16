package com.biblio.backend.service;

import com.biblio.backend.model.Book;
import com.biblio.backend.model.Borrow;
import com.biblio.backend.model.User;
import com.biblio.backend.repository.BookRepository;
import com.biblio.backend.repository.BorrowRepository;
import com.biblio.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor 
public class BorrowService {

    private final BorrowRepository borrowRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public List<Borrow> getMyBorrows(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        return borrowRepository.findByBorrowerOrderByBorrowDateDesc(user);
    }

    @Transactional
    public Borrow createBorrow(Long bookId, String username) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Livre introuvable"));

        if (book.getStock() <= 0) {
            throw new RuntimeException("Livre indisponible (stock épuisé)");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        Borrow borrow = new Borrow();
        borrow.setBook(book);
        borrow.setBorrower(user);
        borrow.setBorrowDate(LocalDate.now());
        borrow.setDueDate(LocalDate.now().plusDays(14)); // +14 jours

        // décrémenter le stock
        book.setStock(book.getStock() - 1);
        bookRepository.save(book);

        return borrowRepository.save(borrow);
    }

    @Transactional
    public void returnBook(Long borrowId, String username) {
        Borrow borrow = borrowRepository.findById(borrowId)
                .orElseThrow(() -> new RuntimeException("Emprunt introuvable"));

        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        // on ne peut rendre que SON emprunt
        if (!borrow.getBorrower().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Vous ne pouvez rendre que vos propres emprunts");
        }

        if (borrow.getReturnDate() != null) {
            throw new RuntimeException("Livre déjà rendu");
        }

        borrow.setReturnDate(LocalDate.now());

        // remettre +1 au stock
        Book book = borrow.getBook();
        book.setStock(book.getStock() + 1);
        bookRepository.save(book);

        borrowRepository.save(borrow);
    }

    @Transactional
    public void deleteBorrow(Long borrowId) {
        Borrow borrow = borrowRepository.findById(borrowId)
            .orElseThrow(() -> new RuntimeException("Emprunt introuvable"));

        if (borrow.getReturnDate() == null) {
            throw new RuntimeException("Impossible de supprimer un emprunt en cours");
        }

        borrowRepository.delete(borrow);
    }
}