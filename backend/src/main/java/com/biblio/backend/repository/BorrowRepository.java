package com.biblio.backend.repository;

import com.biblio.backend.model.Borrow;
import com.biblio.backend.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow, Long> {
    List<Borrow> findByBorrower(User borrower);
    List<Borrow> findByBorrowerOrderByBorrowDateDesc(User borrower); // Nouvelle méthode triée
}