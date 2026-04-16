package models;

import java.time.LocalDateTime;

public class Book {
    // Attributes (match database columns)
    private int bookId;
    private String title;
    private String author;
    private String isbn;
    private int totalCopies;
    private int availableCopies;
    private LocalDateTime createdAt;

    // Constructor 1: For creating new books (without ID - database generates it)
    public Book(String title, String author, String isbn, int totalCopies, int availableCopies) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
    }

    // Constructor 2: For retrieving books from database (with all fields)
    public Book(int bookId, String title, String author, String isbn,
                int totalCopies, int availableCopies, LocalDateTime createdAt) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // toString method for easy printing
    @Override
    public String toString() {
        return String.format("ID: %d | Title: %s | Author: %s | ISBN: %s | Available: %d/%d",
                bookId, title, author, isbn, availableCopies, totalCopies);
    }
}