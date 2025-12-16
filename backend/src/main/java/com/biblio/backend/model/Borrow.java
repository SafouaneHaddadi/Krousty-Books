package com.biblio.backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "borrows")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Borrow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User borrower;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(nullable = false)
    private LocalDate borrowDate;

    @Column
    private LocalDate dueDate; // date de retour prévue (ex: +14 jours)

    @Column
    private LocalDate returnDate; // null tant que pas rendu

    // Méthode utilitaire pour savoir si l'emprunt est en retard
    public boolean isOverdue() {
        if (returnDate != null) return false; // déjà rendu -> pas en retard
        if (dueDate == null) return false;
        return LocalDate.now().isAfter(dueDate);
    }
}