package com.biblio.backend.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biblio.backend.model.Borrow;
import com.biblio.backend.service.BorrowService;

@RestController
@RequestMapping("/api/borrows")
public class BorrowController {

    private final BorrowService borrowService;

    public BorrowController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    // GET /api/borrows (Voir mes emprunts)
    @GetMapping
    public List<Borrow> getMyBorrows(Principal principal) {
        return borrowService.getMyBorrows(principal.getName());
    }

    // POST /api/borrows?bookId=1 (Emprunter un livre)
    @PostMapping
    public ResponseEntity<Borrow> borrowBook(@RequestParam Long bookId, Principal principal) {
        Borrow newBorrow = borrowService.createBorrow(bookId, principal.getName());
        return ResponseEntity.ok(newBorrow);
    }
    
    // PUT /api/borrows/{id}/return (Rendre un livre)
    @PutMapping("/{id}/return")
    public ResponseEntity<Void> returnBook(@PathVariable Long id) {
        borrowService.returnBook(id);
        return ResponseEntity.ok().build();
    }
}