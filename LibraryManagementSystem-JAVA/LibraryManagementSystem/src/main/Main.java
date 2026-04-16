package main;

import service.LibraryService;
import java.util.Scanner;

public class Main {

    private static LibraryService libraryService = new LibraryService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("📚 WELCOME TO LIBRARY MANAGEMENT SYSTEM 📚");
        System.out.println("=".repeat(60) + "\n");

        boolean exit = false;

        while (!exit) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    bookManagementMenu();
                    break;
                case 2:
                    userManagementMenu();
                    break;
                case 3:
                    transactionMenu();
                    break;
                case 4:
                    reportsMenu();
                    break;
                case 5:
                    exit = true;
                    System.out.println("\n👋 Thank you for using Library Management System!");
                    break;
                default:
                    System.out.println("❌ Invalid choice! Please try again.");
            }
        }

        scanner.close();
    }

    // ========== MENUS ==========

    private static void displayMainMenu() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("MAIN MENU");
        System.out.println("=".repeat(60));
        System.out.println("1. 📚 Book Management");
        System.out.println("2. 👥 User Management");
        System.out.println("3. 🔄 Issue/Return Books");
        System.out.println("4. 📊 Reports");
        System.out.println("5. 🚪 Exit");
        System.out.println("=".repeat(60));
    }

    private static void bookManagementMenu() {
        while (true) {
            System.out.println("\n" + "-".repeat(60));
            System.out.println("📚 BOOK MANAGEMENT");
            System.out.println("-".repeat(60));
            System.out.println("1. Add New Book");
            System.out.println("2. View All Books");
            System.out.println("3. Search Book by Title");
            System.out.println("4. Search Book by Author");
            System.out.println("5. Update Book");
            System.out.println("6. Delete Book");
            System.out.println("7. Back to Main Menu");
            System.out.println("-".repeat(60));

            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    libraryService.displayAllBooks();
                    break;
                case 3:
                    searchBookByTitle();
                    break;
                case 4:
                    searchBookByAuthor();
                    break;
                case 5:
                    updateBook();
                    break;
                case 6:
                    deleteBook();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("❌ Invalid choice!");
            }
        }
    }

    private static void userManagementMenu() {
        while (true) {
            System.out.println("\n" + "-".repeat(60));
            System.out.println("👥 USER MANAGEMENT");
            System.out.println("-".repeat(60));
            System.out.println("1. Register New User");
            System.out.println("2. View All Users");
            System.out.println("3. Update User");
            System.out.println("4. Delete User");
            System.out.println("5. Back to Main Menu");
            System.out.println("-".repeat(60));

            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    libraryService.displayAllUsers();
                    break;
                case 3:
                    updateUser();
                    break;
                case 4:
                    deleteUser();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("❌ Invalid choice!");
            }
        }
    }

    private static void transactionMenu() {
        while (true) {
            System.out.println("\n" + "-".repeat(60));
            System.out.println("🔄 TRANSACTION MANAGEMENT");
            System.out.println("-".repeat(60));
            System.out.println("1. Issue Book to User");
            System.out.println("2. Return Book");
            System.out.println("3. View Active Issues");
            System.out.println("4. View User Transaction History");
            System.out.println("5. Back to Main Menu");
            System.out.println("-".repeat(60));

            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    issueBook();
                    break;
                case 2:
                    returnBook();
                    break;
                case 3:
                    libraryService.displayActiveTransactions();
                    break;
                case 4:
                    viewUserTransactions();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("❌ Invalid choice!");
            }
        }
    }

    private static void reportsMenu() {
        while (true) {
            System.out.println("\n" + "-".repeat(60));
            System.out.println("📊 REPORTS");
            System.out.println("-".repeat(60));
            System.out.println("1. All Transactions");
            System.out.println("2. Currently Issued Books");
            System.out.println("3. Overdue Books");
            System.out.println("4. Back to Main Menu");
            System.out.println("-".repeat(60));

            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    libraryService.displayAllTransactions();
                    break;
                case 2:
                    libraryService.displayActiveTransactions();
                    break;
                case 3:
                    libraryService.displayOverdueBooks();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("❌ Invalid choice!");
            }
        }
    }

    // ========== BOOK OPERATIONS ==========

    private static void addBook() {
        System.out.println("\n➕ ADD NEW BOOK");
        String title = getStringInput("Enter book title: ");
        String author = getStringInput("Enter author name: ");
        String isbn = getStringInput("Enter ISBN: ");
        int copies = getIntInput("Enter number of copies: ");

        libraryService.addBook(title, author, isbn, copies);
    }

    private static void searchBookByTitle() {
        String title = getStringInput("\n🔍 Enter title to search: ");
        libraryService.searchBooksByTitle(title);
    }

    private static void searchBookByAuthor() {
        String author = getStringInput("\n🔍 Enter author name to search: ");
        libraryService.searchBooksByAuthor(author);
    }

    private static void updateBook() {
        libraryService.displayAllBooks();
        int bookId = getIntInput("\nEnter Book ID to update: ");

        String title = getStringInput("Enter new title: ");
        String author = getStringInput("Enter new author: ");
        String isbn = getStringInput("Enter new ISBN: ");
        int totalCopies = getIntInput("Enter total copies: ");
        int availableCopies = getIntInput("Enter available copies: ");

        libraryService.updateBook(bookId, title, author, isbn, totalCopies, availableCopies);
    }

    private static void deleteBook() {
        libraryService.displayAllBooks();
        int bookId = getIntInput("\nEnter Book ID to delete: ");
        libraryService.deleteBook(bookId);
    }

    // ========== USER OPERATIONS ==========

    private static void registerUser() {
        System.out.println("\n➕ REGISTER NEW USER");
        String name = getStringInput("Enter user name: ");
        String email = getStringInput("Enter email: ");
        String phone = getStringInput("Enter phone number: ");

        libraryService.registerUser(name, email, phone);
    }

    private static void updateUser() {
        libraryService.displayAllUsers();
        int userId = getIntInput("\nEnter User ID to update: ");

        String name = getStringInput("Enter new name: ");
        String email = getStringInput("Enter new email: ");
        String phone = getStringInput("Enter new phone: ");
        String activeStr = getStringInput("Is active? (yes/no): ");
        boolean isActive = activeStr.equalsIgnoreCase("yes");

        libraryService.updateUser(userId, name, email, phone, isActive);
    }

    private static void deleteUser() {
        libraryService.displayAllUsers();
        int userId = getIntInput("\nEnter User ID to delete: ");
        libraryService.deleteUser(userId);
    }

    // ========== TRANSACTION OPERATIONS ==========

    private static void issueBook() {
        libraryService.displayAllBooks();
        int bookId = getIntInput("\nEnter Book ID to issue: ");

        libraryService.displayAllUsers();
        int userId = getIntInput("\nEnter User ID: ");

        libraryService.issueBook(bookId, userId);
    }

    private static void returnBook() {
        libraryService.displayActiveTransactions();
        int transactionId = getIntInput("\nEnter Transaction ID to return: ");
        libraryService.returnBook(transactionId);
    }

    private static void viewUserTransactions() {
        libraryService.displayAllUsers();
        int userId = getIntInput("\nEnter User ID: ");
        libraryService.displayUserTransactions(userId);
    }

    // ========== UTILITY METHODS ==========

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid number!");
            }
        }
    }
}