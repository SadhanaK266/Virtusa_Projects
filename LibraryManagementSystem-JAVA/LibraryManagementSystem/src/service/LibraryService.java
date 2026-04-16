package service;

import dao.BookDAO;
import dao.UserDAO;
import dao.TransactionDAO;
import models.Book;
import models.User;
import models.Transaction;

import java.time.LocalDate;
import java.util.List;

public class LibraryService {

    private BookDAO bookDAO;
    private UserDAO userDAO;
    private TransactionDAO transactionDAO;

    // Constructor
    public LibraryService() {
        this.bookDAO = new BookDAO();
        this.userDAO = new UserDAO();
        this.transactionDAO = new TransactionDAO();
    }

    // ========== BOOK OPERATIONS ==========

    public void addBook(String title, String author, String isbn, int totalCopies) {
        Book book = new Book(title, author, isbn, totalCopies, totalCopies);
        bookDAO.addBook(book);
    }

    public void displayAllBooks() {
        List<Book> books = bookDAO.getAllBooks();

        if (books.isEmpty()) {
            System.out.println("📚 No books found in the library.");
            return;
        }

        System.out.println("\n" + "=".repeat(100));
        System.out.println("📚 ALL BOOKS IN LIBRARY");
        System.out.println("=".repeat(100));

        for (Book book : books) {
            System.out.println(book);
        }
        System.out.println("=".repeat(100) + "\n");
    }

    public void searchBooksByTitle(String title) {
        List<Book> books = bookDAO.searchBooksByTitle(title);

        if (books.isEmpty()) {
            System.out.println("📚 No books found with title containing: " + title);
            return;
        }

        System.out.println("\n🔍 Search Results:");
        for (Book book : books) {
            System.out.println(book);
        }
    }

    public void searchBooksByAuthor(String author) {
        List<Book> books = bookDAO.searchBooksByAuthor(author);

        if (books.isEmpty()) {
            System.out.println("📚 No books found by author: " + author);
            return;
        }

        System.out.println("\n🔍 Search Results:");
        for (Book book : books) {
            System.out.println(book);
        }
    }

    public void updateBook(int bookId, String title, String author, String isbn, int totalCopies, int availableCopies) {
        Book book = new Book(bookId, title, author, isbn, totalCopies, availableCopies, null);
        bookDAO.updateBook(book);
    }

    public void deleteBook(int bookId) {
        bookDAO.deleteBook(bookId);
    }

    // ========== USER OPERATIONS ==========

    public void registerUser(String name, String email, String phone) {
        User user = new User(name, email, phone, LocalDate.now());
        userDAO.registerUser(user);
    }

    public void displayAllUsers() {
        List<User> users = userDAO.getAllUsers();

        if (users.isEmpty()) {
            System.out.println("👤 No users found.");
            return;
        }

        System.out.println("\n" + "=".repeat(100));
        System.out.println("👥 ALL REGISTERED USERS");
        System.out.println("=".repeat(100));

        for (User user : users) {
            System.out.println(user);
        }
        System.out.println("=".repeat(100) + "\n");
    }

    public void updateUser(int userId, String name, String email, String phone, boolean isActive) {
        User user = new User(userId, name, email, phone, null, isActive);
        userDAO.updateUser(user);
    }

    public void deleteUser(int userId) {
        userDAO.deleteUser(userId);
    }

    // ========== TRANSACTION OPERATIONS ==========

    public void issueBook(int bookId, int userId) {
        // Validate book exists
        Book book = bookDAO.getBookById(bookId);
        if (book == null) {
            System.out.println("❌ Book not found!");
            return;
        }

        // Validate user exists
        User user = userDAO.getUserById(userId);
        if (user == null) {
            System.out.println("❌ User not found!");
            return;
        }

        if (!user.isActive()) {
            System.out.println("❌ User account is inactive!");
            return;
        }

        // Issue the book
        transactionDAO.issueBook(bookId, userId);
    }

    public void returnBook(int transactionId) {
        transactionDAO.returnBook(transactionId);
    }

    public void displayAllTransactions() {
        List<Transaction> transactions = transactionDAO.getAllTransactions();

        if (transactions.isEmpty()) {
            System.out.println("📋 No transactions found.");
            return;
        }

        System.out.println("\n" + "=".repeat(120));
        System.out.println("📋 ALL TRANSACTIONS");
        System.out.println("=".repeat(120));

        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
        System.out.println("=".repeat(120) + "\n");
    }

    public void displayActiveTransactions() {
        List<Transaction> transactions = transactionDAO.getActiveTransactions();

        if (transactions.isEmpty()) {
            System.out.println("📋 No active transactions (all books returned).");
            return;
        }

        System.out.println("\n" + "=".repeat(120));
        System.out.println("📋 CURRENTLY ISSUED BOOKS");
        System.out.println("=".repeat(120));

        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
        System.out.println("=".repeat(120) + "\n");
    }

    public void displayOverdueBooks() {
        List<Transaction> transactions = transactionDAO.getOverdueTransactions();

        if (transactions.isEmpty()) {
            System.out.println("✅ No overdue books! Everyone returned on time.");
            return;
        }

        System.out.println("\n" + "=".repeat(120));
        System.out.println("⚠️  OVERDUE BOOKS (PENDING FINES)");
        System.out.println("=".repeat(120));

        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
        System.out.println("=".repeat(120) + "\n");
    }

    public void displayUserTransactions(int userId) {
        List<Transaction> transactions = transactionDAO.getTransactionsByUser(userId);

        if (transactions.isEmpty()) {
            System.out.println("📋 No transactions found for this user.");
            return;
        }

        System.out.println("\n" + "=".repeat(120));
        System.out.println("📋 USER TRANSACTION HISTORY");
        System.out.println("=".repeat(120));

        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
        System.out.println("=".repeat(120) + "\n");
    }
}