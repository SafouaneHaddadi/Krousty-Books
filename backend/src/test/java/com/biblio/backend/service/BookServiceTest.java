package com.biblio.backend.service;
import com.biblio.backend.model.Book;
import com.biblio.backend.repository.BookRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)  
class BookServiceTest {

    @Mock 
    private BookRepository bookrepository;  

    @InjectMocks
    private BookService bookservice; 
    @Test
    void getAllBooks_shouldReturnAllBooks() {

        //ARRANGE

        List<Book> expectedBooks = new ArrayList<>();
        expectedBooks.add(
             new Book(1L, "Harry Potter", "J.K. Rowling",  "978-123", "Synopsis 1", "Fantasy", "http://image1.jpg", 5)
                 );
        expectedBooks.add(
             new Book(2L, "1984", "George Orwell", "978-456", "Synopsis 2", "Dystopie", "http://image2.jpg", 3)
                 );              

        when(bookrepository.findAll()).thenReturn(expectedBooks);

        //ACT 

        List<Book> actualBooks = bookservice.getAllBooks();

        //ASSERT 

        assertEquals(2, actualBooks.size());  //vérifier que la liste contient 2 elements
        assertEquals("Harry Potter", actualBooks.get(0).getTitle()); 
        assertEquals("Dystopie", actualBooks.get(1).getGenre());

        verify(bookrepository, times(1)).findAll(); // Vérifie que la méthode a été appelée le bon nombre de fois

    }

    @Test
    void getAllBooks_shouldReturnEmptyList_whenNoBooks() {
        
        // ARRANGE 
        when(bookrepository.findAll()).thenReturn(Arrays.asList());

        //ACT 
        List<Book> actualBooks = bookservice.getAllBooks();

        //ASSERT 
        assertTrue(actualBooks.isEmpty()); 
        assertEquals(0, actualBooks.size());

        verify(bookrepository, times(1)).findAll();
}

}