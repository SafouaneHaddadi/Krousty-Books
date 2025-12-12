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


@WebMvcTest(BookController.class) 
public class BookControllerTest {

    @Autowired
    private MockMvc mockmvc;  

    @MockBean 
    private BookService bookservice; 

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

        // ACT & ASSERT 

        mockmvc.perform(get("/api/books")) // Simule GET /api/book
        .andExpect(status().isOk())  
        .andExpect(jsonPath("$", hasSize(2)))              
        .andExpect(jsonPath("$[0].title", is ("Harry Potter")))              
        .andExpect(jsonPath("$[1].genre", is ("Dystopie")));  
        
         verify(bookservice, times(1)).getAllBooks(); //vérifie que le service a été appelé
        
    }


    @Test
    void getAllBooks_returnEmptyJson_whenNoBooks() throws Exception {
        
        when(bookservice.getAllBooks()).thenReturn(Arrays.asList());

        mockmvc.perform(get("/api/books"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(0))); //vérifie tableau JSON vide

        verify(bookservice, times(1)).getAllBooks(); 

    }

}

