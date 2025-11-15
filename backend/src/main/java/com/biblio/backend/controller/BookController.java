package com.biblio.backend.controller;

import com.biblio.backend.model.Book;
import com.biblio.backend.service.BookService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController //Dit à Spring : "Cette classe gère des requêtes HTTP". @ResponseBody automatique : Les retours deviennent du JSON
@RequestMapping("/api/books") //Toutes les routes de ce controller commencent par /api/books

public class BookController {

    @Autowired
    private BookService bookService; //Le Controller a besoin du Service pour la logique métier

    @GetMapping //GET /api/books
    public List<Book> getAllBooks() {
        return bookService.getAllBooks(); //Controller dit : "Je ne connais pas la logique métier, je délègue au Service"
    }

}
