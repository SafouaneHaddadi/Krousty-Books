package com.biblio.backend.controller;

import com.biblio.backend.model.Borrow;
import com.biblio.backend.service.BorrowService;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/borrows")
@RequiredArgsConstructor
public class BorrowController {

    private final BorrowService borrowService;

    @GetMapping
    public List<Borrow> getMyBorrows(Principal principal) {
        return borrowService.getMyBorrows(principal.getName());
    }
    

    @PostMapping
    public ResponseEntity<Borrow> borrowBook(@RequestParam Long bookId, Principal principal) {
        Borrow newBorrow = borrowService.createBorrow(bookId, principal.getName());
        return ResponseEntity.ok(newBorrow);
    }

    // Passage du Principal pour vérifier le propriétaire
    @PutMapping("/{id}/return")
    public ResponseEntity<Void> returnBook(@PathVariable Long id, Principal principal) {
        borrowService.returnBook(id, principal.getName());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // ou dans SecurityConfig si tu préfères
    public ResponseEntity<Void> deleteBorrow(@PathVariable Long id) {
        borrowService.deleteBorrow(id);
        return ResponseEntity.noContent().build();
}
}