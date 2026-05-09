# Smart Pharmacy Management System

## 1. Project Overview

**Smart Pharmacy Management System** is a Java Swing desktop application designed to help a pharmacy manage daily operations digitally. The system supports medicine inventory management, sales records, supplier borrowing records, customer credit/udhaar records, and searching records through a MySQL database.

The main purpose of this project is to reduce manual record keeping and provide a simple, organized, and user-friendly pharmacy management solution.

---

## 2. Student Details

| Name | CMS ID | Section |
|---|---|---|
| Hasaan Ahmed | 023-25-0043 | BSCS-2 'D' |

**Project Type:** Individual Project

---

## 3. GitHub Repository

**GitHub Repository Link:** **https://youtu.be/N4ftKzovxug**

---

## 4. YouTube Demo Video

**YouTube Demo Link:** **https://youtu.be/N4ftKzovxug**

---

## 5. Technologies Used

- **Java** — Core programming language
- **Java Swing** — Graphical User Interface
- **MySQL** — Database management
- **JDBC** — Java database connectivity
- **MySQL Connector/J** — External library for connecting Java with MySQL

---

## 6. Main Features

### Login System
- Authenticates the user before accessing the system.
- Prevents unauthorized access to pharmacy records.

### Dashboard
- Provides navigation to all major modules.
- Acts as the central control panel of the application.

### Medicine Management
- Add new medicine records.
- Update existing medicine information.
- Delete medicine records.
- Display medicine records in a table.

### Sales Management
- Record medicine sales.
- Calculate sale details.
- Update stock after sales.

### Borrow / Supplier Management
- Record medicines borrowed from suppliers.
- Manage supplier-related transactions.
- Track payment status.

### Udhaar / Customer Credit Management
- Record customer credit transactions.
- Track paid and unpaid customer balances.

### Search System
- Search records from different modules.
- Display searched data in a structured table format.

---

## 7. OOP Concepts Used

This project follows an object-oriented structure by separating the application into multiple classes and modules.

### Classes and Objects
Each major part of the system is represented through separate classes, such as:

- `App.java`
- `LoginPage.java`
- `DashBoard.java`
- `Medicine.java`
- `Sale.java`
- `Borrow.java`
- `Udhaar.java`
- `SearchMenu.java`
- `DBConnection.java`

### Encapsulation
Class data and methods are grouped inside relevant classes. For example, the medicine-related interface, validation, database queries, and table display logic are handled inside `Medicine.java`.

### Abstraction
Database connection details are separated into `DBConnection.java`. Other classes use `DBConnection.getConnection()` instead of writing the full connection logic again.

### Inheritance
The GUI classes use Java Swing inheritance by extending Swing components such as `JFrame`. This allows the project to build complete desktop windows using Swing’s built-in object-oriented structure.

### Polymorphism
Java Swing event handling uses polymorphic behavior through listeners such as `ActionListener`, where different buttons perform different actions through event-driven methods.

### Exception Handling
The project uses `try-catch` blocks to handle database connection errors, SQL errors, invalid input, and runtime issues.

---

## 8. Database Information

**Database Name:** `final_project`

The project uses a MySQL database. The database structure is provided in:

```text
Database.sql
```

The SQL file contains the required database tables and default login user.

### Main Tables

- `users`
- `medicine`
- `sales`
- `supply`
- `borrow`
- `udhaar`

---

## 9. Default Login

Use the following credentials after importing the database:

```text
Username: admin
Password: admin123
```

---

## 10. Project Folder Structure

```text
SmartPharmacy/
│
├── src/
│   ├── App.java
│   ├── LoginPage.java
│   ├── DashBoard.java
│   ├── Medicine.java
│   ├── Sale.java
│   ├── Borrow.java
│   ├── Udhaar.java
│   ├── SearchMenu.java
│   └── DBConnection.java
│
├── lib/
│   └── mysql-connector-j-9.7.0.jar
│
├── Database.sql
├── configuration.properties
└── README.md
```

---

## 11. Configuration File Setup

Before running the project, open:

```text
configuration.properties
```

Update the database connection details according to your MySQL setup:

```properties
db.url=jdbc:mysql://localhost:3306/final_project
db.username=root
db.password=your_mysql_password_here
```

### What to Change

- Keep `db.url` the same if your database name is `final_project`.
- Change `db.username` if your MySQL username is not `root`.
- Change `db.password` according to your MySQL password.

Example:

```properties
db.url=jdbc:mysql://localhost:3306/final_project
db.username=root
db.password=12345
```

---

## 12. How to Set Up the Database

### Step 1: Open MySQL Workbench

Open MySQL Workbench or any MySQL client.

### Step 2: Import the Database

Run the provided file:

```text
Database.sql
```

This will create the required database, tables, and default login user.

### Step 3: Confirm Database Name

Make sure the database name is:

```text
final_project
```

---

## 13. How to Run the Project

### Requirements

- JDK installed
- MySQL Server installed
- MySQL Connector/J file available in the `lib` folder

### Compile on Windows

Open terminal inside the project folder and run:

```bash
javac -cp "lib/mysql-connector-j-9.7.0.jar" -d bin src/*.java
```

### Run on Windows

```bash
java -cp "bin;lib/mysql-connector-j-9.7.0.jar" App
```

### Compile on macOS/Linux

```bash
javac -cp "lib/mysql-connector-j-9.7.0.jar" -d bin src/*.java
```

### Run on macOS/Linux

```bash
java -cp "bin:lib/mysql-connector-j-9.7.0.jar" App
```

---

## 14. Important Note About MySQL Connector JAR

The MySQL Connector/J `.jar` file is already included inside the `lib` folder:

```text
lib/mysql-connector-j-9.7.0.jar
```

So the user does **not** need to download or add the JAR file again if the `lib` folder is included with the project. The user only needs to make sure the compile/run command includes this JAR file in the classpath.

If the `lib` folder or JAR file is missing, then the MySQL Connector/J file must be downloaded and added again.

---

## 15. How to Run in VS Code

1. Open the `SmartPharmacy` folder in VS Code.
2. Make sure the `lib/mysql-connector-j-9.7.0.jar` file is present.
3. Make sure `configuration.properties` has the correct MySQL username and password.
4. Import `Database.sql` into MySQL.
5. Run `App.java`.

If VS Code does not detect the JAR automatically, add this in `.vscode/settings.json`:

```json
{
  "java.project.referencedLibraries": [
    "lib/**/*.jar"
  ]
}
```

---

## 16. Academic Integrity Statement

This project was developed as an individual OOP semester project. The code and design are prepared for learning and demonstration purposes. External documentation used includes Java Swing, JDBC, and MySQL Connector/J references.

---

## 17. Future Improvements

- Add profit calculation reports.
- Add low-stock alerts.
- Add print/export functionality.
- Improve dashboard analytics.
- Add role-based access for admin and staff.

---

## 18. Conclusion

The Smart Pharmacy Management System demonstrates a practical Java desktop application using Java Swing, JDBC, MySQL, and object-oriented programming concepts. It provides a structured solution for managing pharmacy records, stock, sales, supplier borrowing, and customer credit records.
