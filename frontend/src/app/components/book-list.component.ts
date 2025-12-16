import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; // contient toutes les fonctionnalités dont on a besoin dans les templates
import { RouterModule } from '@angular/router';
import { Book } from '../models/book.model';
import { BookService } from '../services/book-service';

@Component({
  selector: 'app-book-list',
  standalone: true, 
  imports: [CommonModule, RouterModule], 
  templateUrl: './book-list.component.html'
})

export class BookListComponent implements OnInit {

  books: Book[] = [];          
  loading: boolean = true;     
  errorMessage: string = '';   

  constructor(private bookService: BookService) {}

  ngOnInit(): void {
    this.loadBooks();  // Charge les livres au démarrage
  }

  loadBooks(): void {
    this.loading = true;
    this.errorMessage = '';

    this.bookService.getAllBooks().subscribe({
        //cas succès
      next: (books: Book[]) => {
        this.books = books;    
        this.loading = false;  
      },
      // cas d'erreur
      error: (error: any) => {
        this.errorMessage = 'Impossible de charger les livres';
        this.loading = false;
        console.error('Erreur:', error);
      }
    });
  }

  
}