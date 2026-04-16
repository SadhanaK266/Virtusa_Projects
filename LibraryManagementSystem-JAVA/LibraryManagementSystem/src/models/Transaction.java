package models;

import java.time.LocalDate;

public class Transaction {
    // Attributes
    private int transactionId;
    private int bookId;
    private int userId;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private double fineAmount;
    private String status;  // "ISSUED" or "RETURNED"

    // For displaying - we'll store book and user names too
    private String bookTitle;
    private String userName;

    // Constructor 1: For issuing a book (no transaction ID yet)
    public Transaction(int bookId, int userId, LocalDate issueDate, LocalDate dueDate) {
        this.bookId = bookId;
        this.userId = userId;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.status = "ISSUED";
        this.fineAmount = 0.0;
    }

    // Constructor 2: For retrieving from database (all fields)
    public Transaction(int transactionId, int bookId, int userId,
                       LocalDate issueDate, LocalDate dueDate, LocalDate returnDate,
                       double fineAmount, String status) {
        this.transactionId = transactionId;
        this.bookId = bookId;
        this.userId = userId;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.fineAmount = fineAmount;
        this.status = status;
    }

    // Getters and Setters
    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public double getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(double fineAmount) {
        this.fineAmount = fineAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return String.format("Transaction ID: %d | Book: %s | User: %s | Issue: %s | Due: %s | Status: %s | Fine: ₹%.2f",
                transactionId, bookTitle, userName, issueDate, dueDate, status, fineAmount);
    }
}