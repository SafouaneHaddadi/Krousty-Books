package com.biblio.backend.controller;

import com.biblio.backend.model.Book;
import com.biblio.backend.service.BookService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/api/books")

public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping //GET /api/books
    @CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8082"})  //Sans cette config, le navigateur bloque les appels API entre le frontend et le backend
    public List<Book> getAllBooks() {
        return bookService.getAllBooks(); // délégue la logique métier au service
    }

    // Endpoint DELETE /api/books/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) { // renvoit un code pour montrer le resultat de la requete http

        try {
            // On appelle le service pour supprimer le livre
            bookService.deleteBook(id);

            // Si tout se passe bien → retour 204 (aucun contenu)
            return ResponseEntity.noContent().build();

        } catch (RuntimeException e) {
            // Si le livre n'existe pas → on renvoie 404 (Not Found)
            return ResponseEntity.notFound().build();
        }
    }
}
