# 📚 Library Management System

> A complete Java console application for managing library operations - books, users, and transactions with automated fine calculation.

![Java](https://img.shields.io/badge/Java-21-orange)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
![Status](https://img.shields.io/badge/Status-Complete-success)

---

## 🎯 What Does This Do?

Automates library management:
- ➕ Add/Update/Delete books
- 👤 Register library members
- 📖 Issue & return books (14-day borrowing period)
- 💰 Auto-calculate fines (₹10/day for late returns)
- 🔍 Search books by title or author
- 📊 Generate reports (transactions, overdue books)

---

## 🛠️ Tech Stack

| Technology | Purpose |
|------------|---------|
| **Java 21** | Core programming |
| **MySQL 8.0** | Database |
| **JDBC** | Database connectivity |
| **OOP Concepts** | Encapsulation, Abstraction, Modularity |
| **DAO Pattern** | Clean separation of database logic |

---

## 📁 Project Structure
LibraryManagementSystem/
├── src/
│ ├── dao/ # Database operations
│ ├── models/ # Book, User, Transaction classes
│ ├── service/ # Business logic
│ ├── util/ # Database connection
│ └── main/ # Application entry point
├── lib/ # MySQL JDBC driver
├── database/ # SQL schema
└── README.md

---

## 🗄️ Database Design

### **3 Tables with Relationships:**
<!-- BOOKS (1) ──< TRANSACTIONS >── (1) USERS -->/

**books** → Stores book inventory  
**users** → Library members  
**transactions** → Issue/return records with fines  

---

## 🚀 Quick Setup

### **Prerequisites**
- Java JDK 17+
- MySQL 8.0+
- Git

### **Installation (5 minutes)**

```bash
# 1. Clone repository
git clone https://github.com/SadhanaK266/LibraryManagementSystem.git
cd LibraryManagementSystem

# 2. Setup database
mysql -u root -p < database/schema.sql

# 3. Configure database credentials
# Edit: src/util/DatabaseConnection.java
# Update: USERNAME and PASSWORD

# 4. Add JDBC driver to IDE
# IntelliJ: File → Project Structure → Libraries → Add lib/mysql-connector-j-9.1.0.jar

# 5. Run application
# Right-click Main.java → Run

📸 Quick Demo
Main Menu

============================================================
📚 WELCOME TO LIBRARY MANAGEMENT SYSTEM 📚
============================================================
1. 📚 Book Management
2. 👥 User Management
3. 🔄 Issue/Return Books
4. 📊 Reports
5. 🚪 Exit
============================================================

Sample Output

✅ Book added successfully!
✅ User registered successfully!
✅ Book issued successfully! Due date: 2025-01-28
⚠️  Book returned late! Fine: ₹50.0

✨ Key Features
Feature Description
SmartSearch	Find books by title/author (partial match)
Auto-Fine	₹10/day calculated automatically
Real-time Updates	Available copies update instantly
Transaction History	Complete audit trail
Overdue Tracking	Identify late returns


💻 Core Code Concepts

OOP Implementation

// Encapsulation
private String title;
public String getTitle() {return title;}

// DAO Pattern
BookDAO bookDAO = new BookDAO();
bookDAO.addBook(book);  // Clean separation

SQL JOIN Example

SELECT t.*, b.title, u.name 
FROM transactions t
JOIN books b ON t.book_id = b.book_id
JOIN users u ON t.user_id = u.user_id
WHERE t.status = 'ISSUED';


Fine Calculation

long daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);
double fine = daysLate * 10.0;  // ₹10 per day

🧪 Testing Checklist
 Add/Update/Delete books
 Register users
 Issue books (availability decreases)
 Return books (fine calculation)
 Search functionality
 All reports working
📚 What I Learned
Java Skills
✅ OOP (Encapsulation, Abstraction)
✅ JDBC & PreparedStatement
✅ Exception handling
✅ Date/Time API

Database
✅ MySQL schema design
✅ Foreign keys & relationships
✅ JOIN operations
✅ CRUD operations

Software Design
✅ DAO pattern
✅ Layered architecture
✅ Code modularity

🔮 Future Enhancements
 JavaFX GUI
 Email notifications for due dates
 Barcode scanning
 Export reports to PDF
 Multi-user authentication



👨‍💻 Author
Your Sadhana K
📧 sadhanakaruppusamy266@gmail.com

Date: April 2026




