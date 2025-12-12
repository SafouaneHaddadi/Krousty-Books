package com.biblio.backend.service;

import com.biblio.backend.model.Book;
import com.biblio.backend.repository.BookRepository; //BookRepository : Interface pour communiquer avec la base de données
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll(); // appel à JPA qui génère le sql
    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Livre introuvable");
        }

        bookRepository.deleteById(id);
    }
}
