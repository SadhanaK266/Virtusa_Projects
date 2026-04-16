package dao;

import models.User;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // 1. REGISTER NEW USER
    public boolean registerUser(User user) {
        String query = "INSERT INTO users (name, email, phone, membership_date, is_active) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPhone());
            pstmt.setDate(4, Date.valueOf(user.getMembershipDate()));
            pstmt.setBoolean(5, user.isActive());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("✅ User registered successfully!");
                return true;
            }

        } catch (SQLException e) {
            System.out.println("❌ Error registering user: " + e.getMessage());
        }
        return false;
    }

    // 2. GET ALL USERS
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                User user = new User(
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getDate("membership_date").toLocalDate(),
                        rs.getBoolean("is_active")
                );
                users.add(user);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error fetching users: " + e.getMessage());
        }
        return users;
    }

    // 3. GET USER BY ID
    public User getUserById(int userId) {
        String query = "SELECT * FROM users WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getDate("membership_date").toLocalDate(),
                        rs.getBoolean("is_active")
                );
            }

        } catch (SQLException e) {
            System.out.println("❌ Error fetching user: " + e.getMessage());
        }
        return null;
    }

    // 4. SEARCH USER BY EMAIL
    public User getUserByEmail(String email) {
        String query = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getDate("membership_date").toLocalDate(),
                        rs.getBoolean("is_active")
                );
            }

        } catch (SQLException e) {
            System.out.println("❌ Error fetching user: " + e.getMessage());
        }
        return null;
    }

    // 5. UPDATE USER
    public boolean updateUser(User user) {
        String query = "UPDATE users SET name = ?, email = ?, phone = ?, is_active = ? WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPhone());
            pstmt.setBoolean(4, user.isActive());
            pstmt.setInt(5, user.getUserId());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("✅ User updated successfully!");
                return true;
            }

        } catch (SQLException e) {
            System.out.println("❌ Error updating user: " + e.getMessage());
        }
        return false;
    }

    // 6. DELETE USER
    public boolean deleteUser(int userId) {
        String query = "DELETE FROM users WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, userId);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("✅ User deleted successfully!");
                return true;
            }

        } catch (SQLException e) {
            System.out.println("❌ Error deleting user: " + e.getMessage());
        }
        return false;
    }

    // 7. GET ACTIVE USERS ONLY
    public List<User> getActiveUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users WHERE is_active = true";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                User user = new User(
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getDate("membership_date").toLocalDate(),
                        rs.getBoolean("is_active")
                );
                users.add(user);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error fetching active users: " + e.getMessage());
        }
        return users;
    }
}