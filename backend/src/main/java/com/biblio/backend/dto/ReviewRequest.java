package com.biblio.backend.dto;

/*  avec DTO, on contrôle exactement ce que le client peut envoyer (ici, seulement bookId, rating, comment)
   on évite ainsi les problèmes de sérialisation JSON avec les entités JPA  (pas de book/user complets dans le body) */
public class ReviewRequest {

    private Long bookId;    
    private int rating;    
    private String comment; 

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
    
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    
    public Long getBookId() { return bookId; }
    public void setBookId(Long bookId) { this.bookId = bookId; }
    
}
