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


@ExtendWith(MockitoExtension.class)  // Active Mockito dans le test JUnit 5. Mockito permet de créer des "faux" objets pour isoler le test.
class BookServiceTest {
    @Mock 
    private BookRepository bookrepository;  //  Crée un mock (simulation) de BookRepository. On ne veut pas tester la vraie base de données, seulement la logique du Service. Le mock nous permet de contrôler ce que "retourne" le Repository

    @InjectMocks
    private BookService bookservice; // "Prends le VRAI BookService et injecte-y le FAUX Repository". Résultat : bookservice.booksepository = FAUX (le mock)|||  bookService = VRAI (celui que tu as codé)

    @Test
    void getAllBooks_shouldReturnAllBooks() {

        //ARRANGE : préparer les données 

        List<Book> expectedBooks = new ArrayList<>();
        expectedBooks.add(
             new Book(1L, "Harry Potter", "J.K. Rowling",  "978-123", "Synopsis 1", "Fantasy", "http://image1.jpg", 5)
                 );
        expectedBooks.add(
             new Book(2L, "1984", "George Orwell", "978-456", "Synopsis 2", "Dystopie", "http://image2.jpg", 3)
                 );              

        /* Book book1 = new Book(1L, "Harry Potter", "J.K. Rowling", 
                             "978-123", "Fantasy", "Synopsis 1", "http://image1.jpg", 5);
        Book book2 = new Book(2L, "1984", "George Orwell", 
                             "978-456", "Dystopie", "Synopsis 2", "http://image2.jpg", 3);

        List<Book> books = Arrays.asList(book1, book2);          */        

        // Configurer le mock : quand findAll() est appelé, retourne books
        // Quand la méthode X est appelée sur le mock, retourne Y". On contrôle le comportement du mock.
        when(bookrepository.findAll()).thenReturn(expectedBooks);

        //ACT : appeler la méthode à tester

        List<Book> actualBooks = bookservice.getAllBooks();

        //ASSERT : vérifier le résultat

        assertEquals(2, actualBooks.size());  //vérifier que la liste contient 2 elements
        assertEquals("Harry Potter", actualBooks.get(0).getTitle()); 
        assertEquals("Dystopie", actualBooks.get(1).getGenre());

        verify(bookrepository, times(1)).findAll(); // Vérifie que la méthode a été appelée le bon nombre de fois. Ici, on s'assure que le Service appelle bien le Repository.

    }

    @Test
    void getAllBooks_shouldReturnEmptyList_whenNoBooks() {
        
        // ARRANGE : Configurer le mock pour retourner une liste vide
        when(bookrepository.findAll()).thenReturn(Arrays.asList());

        //ACT : appeler la méthode
        List<Book> actualBooks = bookservice.getAllBooks();

        //ASSERT : vérifier que la liste est vide 

        assertTrue(actualBooks.isEmpty()); 
        assertEquals(0, actualBooks.size());

        verify(bookrepository, times(1)).findAll();
}

}