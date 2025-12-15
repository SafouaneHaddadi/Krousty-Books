package com.biblio.backend.controller;

import com.biblio.backend.dto.BorrowResponse;
import com.biblio.backend.model.Borrow;
import com.biblio.backend.service.BorrowService;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/borrows")
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    // GET /api/borrows (Voir mes emprunts)
    @GetMapping
    public List<BorrowResponse> getMyBorrows(Principal principal) {
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
    public ResponseEntity<Void> returnBook(@PathVariable Long id, Principal principal) {
        borrowService.returnBook(id, principal.getName()); // AJOUT DU 2ème PARAMÈTRE
        return ResponseEntity.ok().build();
    }
}