import { Routes } from '@angular/router';
import { BookListComponent } from './components/book-list.component';

export const routes: Routes = [
  { 
    path: '', 
    component: BookListComponent 
  },
  
  // Redirection pour les routes inconnues
  { 
    path: '**', 
    redirectTo: '' 
  }
];