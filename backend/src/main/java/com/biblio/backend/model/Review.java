package com.biblio.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rating; 

    @Column(length = 1000)
    private String comment; 

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Review() {
        this.createdAt = LocalDateTime.now();
    }
    public Review(int rating, String comment, Book book, User user) {
        this.rating = rating;
        this.comment = comment;
        this.book = book;
        this.user = user;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}