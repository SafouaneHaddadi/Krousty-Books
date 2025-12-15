package com.biblio.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblio.backend.model.Borrow;
import com.biblio.backend.model.User;

public interface BorrowRepository extends JpaRepository<Borrow, Long> {
    // Trouver tous les emprunts d'un utilisateur spécifique
    List<Borrow> findByBorrower(User borrower);
}