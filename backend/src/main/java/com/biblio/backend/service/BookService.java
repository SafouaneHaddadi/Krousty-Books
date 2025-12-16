package com.biblio.backend.service;

import java.util.List;

import org.springframework.stereotype.Service; //BookRepository : Interface pour communiquer avec la base de données

import com.biblio.backend.model.Book;
import com.biblio.backend.repository.BookRepository;

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

    public Book updateBook(Long id, Book bookDetails) {
        // 1. On cherche le livre (sinon erreur)
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livre introuvable avec l'ID : " + id));

        // 2. On met à jour les infos
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setIsbn(bookDetails.getIsbn());
        book.setSynopsis(bookDetails.getSynopsis());
        book.setGenre(bookDetails.getGenre());
        book.setImageUrl(bookDetails.getImageUrl());

        // 3. On vérifie le stock (pas de négatif)
        if (bookDetails.getStock() != null && bookDetails.getStock() >= 0) {
            book.setStock(bookDetails.getStock());
        }

        // 4. On sauvegarde
        return bookRepository.save(book);
    }
public Book getBookById(Long id) {
    return bookRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Livre non trouvé"));
}
}
