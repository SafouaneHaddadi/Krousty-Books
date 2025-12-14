package com.biblio.backend.controller;

import com.biblio.backend.model.Book;
import com.biblio.backend.repository.BookRepository;
import com.biblio.backend.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;     

    @MockBean
    private BookRepository bookRepository; 

    @Test
    public void getAllBooks_returnCode200AndBooks_whenBooksExist() throws Exception {
    
        Book book1 = new Book();
        book1.setTitle("Harry Potter");
        List<Book> books = Arrays.asList(book1);
        
        when(bookService.getAllBooks()).thenReturn(books);

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Harry Potter"));
    }

    @Test
    public void addBook_returnCode201_whenValidBook() throws Exception {
        Book book = new Book();
        book.setTitle("Nouveau Livre");
        book.setAuthor("Moi");
        
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isCreated());
    }
}