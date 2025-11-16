package com.biblio.backend.service;

import com.biblio.backend.model.Book;
import com.biblio.backend.repository.BookRepository; //BookRepository : Interface pour communiquer avec la base de données
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service 
public class BookService { 

    @Autowired 
    private BookRepository bookRepository; 

    public List<Book> getAllBooks() {
       return bookRepository.findAll(); // appel à JPA qui génère le sql
}
}