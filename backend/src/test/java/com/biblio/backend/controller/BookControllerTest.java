package com.biblio.backend.controller;

import com.biblio.backend.model.Book;
import com.biblio.backend.service.BookService;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(BookController.class) // ← Test seulement le layer Web (Controller)
public class BookControllerTest {

    @Autowired
    private MockMvc mockmvc;   // ← Simule les requêtes HTTP

    @MockBean
    private BookService bookservice; // ← Mock du Service (comme @Mock mais pour Spring)

    @Test 
    void getAllBooks_returnCode200AndBooks_whenBooksExist() throws Exception {

        //ARRANGE

        List<Book> expectedBooks = new ArrayList<>();
        expectedBooks.add(
             new Book(1L, "Harry Potter", "J.K. Rowling",  "978-123", "Synopsis 1", "Fantasy", "http://image1.jpg", 5)
                 );
        expectedBooks.add(
             new Book(2L, "1984", "George Orwell", "978-456", "Synopsis 2", "Dystopie", "http://image2.jpg", 3)
                 );   
                 
        when(bookservice.getAllBooks()).thenReturn(expectedBooks); //vérifie que le Service a été appelé 

        // ACT & ASSERT : Exécute la requête et vérifie la réponse

        mockmvc.perform(get("/api/books")) // ← Simule GET /api/book
        .andExpect(status().isOk())  //doit renvoyer status 200
        .andExpect(jsonPath("$", hasSize(2)))               //dans le test du service on écrivait  assertEquals(2, actualBooks.size());
        .andExpect(jsonPath("$[0].title", is ("Harry Potter")))                //dans le tets du service on écrivait assertEquals("Harry Potter", actualBooks.get(0).getTitle()); 
        .andExpect(jsonPath("$[1].genre", is ("Dystopie")));   // dans le test du service on écrivait         assertEquals("Dystopie", actualBooks.get(1).getGenre());
        
         verify(bookservice, times(1)).getAllBooks(); //vérifie que le service a été appelé
        
    }


    @Test
    void getAllBooks_returnEmptyJson_whenNoBooks() throws Exception {
        
        when(bookservice.getAllBooks()).thenReturn(Arrays.asList());

        mockmvc.perform(get("/api/books"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(0))); //vérifie tableau JSON vide

        verify(bookservice, times(1)).getAllBooks(); //vérifie que le service a été appelé

    }

}


/* 
 
🎯 CE QUE CE TEST GARANTIT :
Fonctionnement HTTP :

    ✅ URL : GET /api/books répond

    ✅ Status : 200 OK

    ✅ Format : JSON valide

    ✅ Contenu : Données correctes


    🔧 SI LE TEST ÉCHOUE :
    Cas 1 : Status ≠ 200

    → Problème dans le Controller (exception non gérée)
    Cas 2 : JSON mal formé

    → Problème de sérialisation Jackson
    Cas 3 : Données incorrectes

    → Problème dans le Service ou le mapping
    Cas 4 : Service non appelé

    → Bug dans le Controller

    Ce test est ton GARDIEN de l'API REST ! 🛡️
 */