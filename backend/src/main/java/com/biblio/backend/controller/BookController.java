package com.biblio.backend.controller;

import com.biblio.backend.model.Book; 
import com.biblio.backend.repository.BookRepository; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.HttpStatus; 
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "http://localhost:4200") 
public class BookController {

 

    @Autowired 
    private BookRepository bookRepository;

 
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        try {
            
            Book newBook = bookRepository.save(book);
            
      
            return new ResponseEntity<>(newBook, HttpStatus.CREATED);
        } catch (Exception e) {
           
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    
}