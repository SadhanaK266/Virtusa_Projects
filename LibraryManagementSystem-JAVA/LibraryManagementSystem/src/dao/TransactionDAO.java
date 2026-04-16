package dao;

import models.Transaction;
import util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    private BookDAO bookDAO = new BookDAO();

    // 1. ISSUE BOOK TO USER
    public boolean issueBook(int bookId, int userId) {
        // Check if book is available
        String checkQuery = "SELECT available_copies FROM books WHERE book_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {

            checkStmt.setInt(1, bookId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                int availableCopies = rs.getInt("available_copies");

                if (availableCopies <= 0) {
                    System.out.println("❌ Book not available! All copies are issued.");
                    return false;
                }

                // Issue the book
                LocalDate issueDate = LocalDate.now();
                LocalDate dueDate = issueDate.plusDays(14);  // 14 days borrowing period

                String insertQuery = "INSERT INTO transactions (book_id, user_id, issue_date, due_date, status) VALUES (?, ?, ?, ?, 'ISSUED')";

                try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                    pstmt.setInt(1, bookId);
                    pstmt.setInt(2, userId);
                    pstmt.setDate(3, Date.valueOf(issueDate));
                    pstmt.setDate(4, Date.valueOf(dueDate));

                    int rowsAffected = pstmt.executeUpdate();

                    if (rowsAffected > 0) {
                        // Decrease available copies
                        bookDAO.updateAvailableCopies(bookId, availableCopies - 1);
                        System.out.println("✅ Book issued successfully! Due date: " + dueDate);
                        return true;
                    }
                }

            } else {
                System.out.println("❌ Book not found!");
            }

        } catch (SQLException e) {
            System.out.println("❌ Error issuing book: " + e.getMessage());
        }
        return false;
    }

    // 2. RETURN BOOK
    public boolean returnBook(int transactionId) {
        // Get transaction details
        String selectQuery = "SELECT book_id, due_date FROM transactions WHERE transaction_id = ? AND status = 'ISSUED'";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {

            selectStmt.setInt(1, transactionId);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                int bookId = rs.getInt("book_id");
                LocalDate dueDate = rs.getDate("due_date").toLocalDate();
                LocalDate returnDate = LocalDate.now();

                // Calculate fine (₹10 per day after due date)
                double fine = 0.0;
                if (returnDate.isAfter(dueDate)) {
                    long daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);
                    fine = daysLate * 10.0;  // ₹10 per day
                    System.out.println("⚠️  Book returned late! Fine: ₹" + fine);
                }

                // Update transaction
                String updateQuery = "UPDATE transactions SET return_date = ?, fine_amount = ?, status = 'RETURNED' WHERE transaction_id = ?";

                try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                    updateStmt.setDate(1, Date.valueOf(returnDate));
                    updateStmt.setDouble(2, fine);
                    updateStmt.setInt(3, transactionId);

                    int rowsAffected = updateStmt.executeUpdate();

                    if (rowsAffected > 0) {
                        // Increase available copies
                        String bookQuery = "SELECT available_copies FROM books WHERE book_id = ?";
                        try (PreparedStatement bookStmt = conn.prepareStatement(bookQuery)) {
                            bookStmt.setInt(1, bookId);
                            ResultSet bookRs = bookStmt.executeQuery();

                            if (bookRs.next()) {
                                int currentAvailable = bookRs.getInt("available_copies");
                                bookDAO.updateAvailableCopies(bookId, currentAvailable + 1);
                            }
                        }

                        System.out.println("✅ Book returned successfully!");
                        if (fine > 0) {
                            System.out.println("💰 Total fine: ₹" + fine);
                        }
                        return true;
                    }
                }

            } else {
                System.out.println("❌ Transaction not found or book already returned!");
            }

        } catch (SQLException e) {
            System.out.println("❌ Error returning book: " + e.getMessage());
        }
        return false;
    }

    // 3. GET ALL TRANSACTIONS
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT t.*, b.title as book_title, u.name as user_name " +
                "FROM transactions t " +
                "JOIN books b ON t.book_id = b.book_id " +
                "JOIN users u ON t.user_id = u.user_id " +
                "ORDER BY t.transaction_id DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Transaction transaction = new Transaction(
                        rs.getInt("transaction_id"),
                        rs.getInt("book_id"),
                        rs.getInt("user_id"),
                        rs.getDate("issue_date").toLocalDate(),
                        rs.getDate("due_date").toLocalDate(),
                        rs.getDate("return_date") != null ? rs.getDate("return_date").toLocalDate() : null,
                        rs.getDouble("fine_amount"),
                        rs.getString("status")
                );

                transaction.setBookTitle(rs.getString("book_title"));
                transaction.setUserName(rs.getString("user_name"));

                transactions.add(transaction);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error fetching transactions: " + e.getMessage());
        }
        return transactions;
    }

    // 4. GET ACTIVE TRANSACTIONS (Currently Issued Books)
    public List<Transaction> getActiveTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT t.*, b.title as book_title, u.name as user_name " +
                "FROM transactions t " +
                "JOIN books b ON t.book_id = b.book_id " +
                "JOIN users u ON t.user_id = u.user_id " +
                "WHERE t.status = 'ISSUED' " +
                "ORDER BY t.due_date ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Transaction transaction = new Transaction(
                        rs.getInt("transaction_id"),
                        rs.getInt("book_id"),
                        rs.getInt("user_id"),
                        rs.getDate("issue_date").toLocalDate(),
                        rs.getDate("due_date").toLocalDate(),
                        null,
                        0.0,
                        rs.getString("status")
                );

                transaction.setBookTitle(rs.getString("book_title"));
                transaction.setUserName(rs.getString("user_name"));

                transactions.add(transaction);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error fetching active transactions: " + e.getMessage());
        }
        return transactions;
    }

    // 5. GET TRANSACTIONS BY USER
    public List<Transaction> getTransactionsByUser(int userId) {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT t.*, b.title as book_title, u.name as user_name " +
                "FROM transactions t " +
                "JOIN books b ON t.book_id = b.book_id " +
                "JOIN users u ON t.user_id = u.user_id " +
                "WHERE t.user_id = ? " +
                "ORDER BY t.transaction_id DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Transaction transaction = new Transaction(
                        rs.getInt("transaction_id"),
                        rs.getInt("book_id"),
                        rs.getInt("user_id"),
                        rs.getDate("issue_date").toLocalDate(),
                        rs.getDate("due_date").toLocalDate(),
                        rs.getDate("return_date") != null ? rs.getDate("return_date").toLocalDate() : null,
                        rs.getDouble("fine_amount"),
                        rs.getString("status")
                );

                transaction.setBookTitle(rs.getString("book_title"));
                transaction.setUserName(rs.getString("user_name"));

                transactions.add(transaction);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error fetching user transactions: " + e.getMessage());
        }
        return transactions;
    }

    // 6. GET OVERDUE BOOKS
    public List<Transaction> getOverdueTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT t.*, b.title as book_title, u.name as user_name " +
                "FROM transactions t " +
                "JOIN books b ON t.book_id = b.book_id " +
                "JOIN users u ON t.user_id = u.user_id " +
                "WHERE t.status = 'ISSUED' AND t.due_date < CURDATE() " +
                "ORDER BY t.due_date ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Transaction transaction = new Transaction(
                        rs.getInt("transaction_id"),
                        rs.getInt("book_id"),
                        rs.getInt("user_id"),
                        rs.getDate("issue_date").toLocalDate(),
                        rs.getDate("due_date").toLocalDate(),
                        null,
                        0.0,
                        rs.getString("status")
                );

                transaction.setBookTitle(rs.getString("book_title"));
                transaction.setUserName(rs.getString("user_name"));

                // Calculate pending fine
                LocalDate today = LocalDate.now();
                LocalDate dueDate = rs.getDate("due_date").toLocalDate();
                long daysLate = ChronoUnit.DAYS.between(dueDate, today);
                transaction.setFineAmount(daysLate * 10.0);

                transactions.add(transaction);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error fetching overdue transactions: " + e.getMessage());
        }
        return transactions;
    }

    // 7. GET TRANSACTION BY ID
    public Transaction getTransactionById(int transactionId) {
        String query = "SELECT t.*, b.title as book_title, u.name as user_name " +
                "FROM transactions t " +
                "JOIN books b ON t.book_id = b.book_id " +
                "JOIN users u ON t.user_id = u.user_id " +
                "WHERE t.transaction_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, transactionId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Transaction transaction = new Transaction(
                        rs.getInt("transaction_id"),
                        rs.getInt("book_id"),
                        rs.getInt("user_id"),
                        rs.getDate("issue_date").toLocalDate(),
                        rs.getDate("due_date").toLocalDate(),
                        rs.getDate("return_date") != null ? rs.getDate("return_date").toLocalDate() : null,
                        rs.getDouble("fine_amount"),
                        rs.getString("status")
                );

                transaction.setBookTitle(rs.getString("book_title"));
                transaction.setUserName(rs.getString("user_name"));

                return transaction;
            }

        } catch (SQLException e) {
            System.out.println("❌ Error fetching transaction: " + e.getMessage());
        }
        return null;
    }
}