package dao;

import models.Book;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    // 1. ADD NEW BOOK
    public boolean addBook(Book book) {
        String query = "INSERT INTO books (title, author, isbn, total_copies, available_copies) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getIsbn());
            pstmt.setInt(4, book.getTotalCopies());
            pstmt.setInt(5, book.getAvailableCopies());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("✅ Book added successfully!");
                return true;
            }

        } catch (SQLException e) {
            System.out.println("❌ Error adding book: " + e.getMessage());
        }
        return false;
    }

    // 2. GET ALL BOOKS
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Book book = new Book(
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("isbn"),
                        rs.getInt("total_copies"),
                        rs.getInt("available_copies"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                );
                books.add(book);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error fetching books: " + e.getMessage());
        }
        return books;
    }

    // 3. SEARCH BOOKS BY TITLE
    public List<Book> searchBooksByTitle(String title) {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books WHERE title LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, "%" + title + "%");  // % for partial matching
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Book book = new Book(
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("isbn"),
                        rs.getInt("total_copies"),
                        rs.getInt("available_copies"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                );
                books.add(book);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error searching books: " + e.getMessage());
        }
        return books;
    }

    // 4. SEARCH BOOKS BY AUTHOR
    public List<Book> searchBooksByAuthor(String author) {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books WHERE author LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, "%" + author + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Book book = new Book(
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("isbn"),
                        rs.getInt("total_copies"),
                        rs.getInt("available_copies"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                );
                books.add(book);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error searching books: " + e.getMessage());
        }
        return books;
    }

    // 5. GET BOOK BY ID
    public Book getBookById(int bookId) {
        String query = "SELECT * FROM books WHERE book_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, bookId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Book(
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("isbn"),
                        rs.getInt("total_copies"),
                        rs.getInt("available_copies"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                );
            }

        } catch (SQLException e) {
            System.out.println("❌ Error fetching book: " + e.getMessage());
        }
        return null;
    }

    // 6. UPDATE BOOK
    public boolean updateBook(Book book) {
        String query = "UPDATE books SET title = ?, author = ?, isbn = ?, total_copies = ?, available_copies = ? WHERE book_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getIsbn());
            pstmt.setInt(4, book.getTotalCopies());
            pstmt.setInt(5, book.getAvailableCopies());
            pstmt.setInt(6, book.getBookId());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("✅ Book updated successfully!");
                return true;
            }

        } catch (SQLException e) {
            System.out.println("❌ Error updating book: " + e.getMessage());
        }
        return false;
    }

    // 7. DELETE BOOK
    public boolean deleteBook(int bookId) {
        String query = "DELETE FROM books WHERE book_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, bookId);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("✅ Book deleted successfully!");
                return true;
            }

        } catch (SQLException e) {
            System.out.println("❌ Error deleting book: " + e.getMessage());
        }
        return false;
    }

    // 8. UPDATE AVAILABLE COPIES (used when issuing/returning)
    public boolean updateAvailableCopies(int bookId, int newAvailableCount) {
        String query = "UPDATE books SET available_copies = ? WHERE book_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, newAvailableCount);
            pstmt.setInt(2, bookId);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error updating available copies: " + e.getMessage());
        }
        return false;
    }
}