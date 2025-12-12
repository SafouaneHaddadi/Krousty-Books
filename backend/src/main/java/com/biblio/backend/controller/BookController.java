package com.biblio.backend.controller;

import com.biblio.backend.model.Book;
import com.biblio.backend.repository.BookRepository;
import com.biblio.backend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8082"})
public class BookController {

    @Autowired
    private BookRepository bookRepository; 
    @Autowired
    private BookService bookService;    


    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }
  
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        try {
            Book newBook = bookRepository.save(book);
            return new ResponseEntity<>(newBook, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        try {
            bookService.deleteBook(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
} 