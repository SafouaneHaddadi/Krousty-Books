package com.biblio.backend.controller;

import com.biblio.backend.model.Book;
import com.biblio.backend.service.BookService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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

}
