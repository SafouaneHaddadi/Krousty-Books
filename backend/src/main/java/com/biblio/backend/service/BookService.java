package com.biblio.backend.service;

import com.biblio.backend.model.Book;
import com.biblio.backend.repository.BookRepository; //BookRepository : Interface pour communiquer avec la base de données
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service // Dit à Spring "Cette classe est un Service, gère-la automatiquement"
public class BookService { // BookService : Logique métier de l'application

    @Autowired // Injection de dépendance : Spring va créer une instance de BookRepository et l'injecter ici. 
    private BookRepository bookRepository; //Magie Spring Data JPA : Spring implémente AUTOMATIQUEMENT les méthodes

    /* Sans @Autowired on aurait du écrire : 

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;  // ← Injection MANUELLE
    }
     
     */

    public List<Book> getAllBooks() {
       return bookRepository.findAll(); //bookRepository.findAll() : Appel à JPA qui génère SELECT * FROM books. Service dit : "Je ne connais pas la base de données, je délègue au Repository"  
    }

}

/* 

   Ce code sépare les responsabilités :

    BookRepository : S'occupe de la base de données

    BookService : S'occupe de la logique métier

    BookController : S'occupe des requêtes HTTP

Demain, si tu veux :

    Changer de base de données → Seul Repository est impacté

    Ajouter des règles métier → Seul Service est impacté

    Changer l'API REST → Seul Controller est impacté

C'est la puissance de l'architecture Spring !


*/