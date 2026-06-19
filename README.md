# Handicap University Management

A Java desktop application for managing university services related to students with disabilities.

## Main Features

- User authentication
- User management
- Demande submission and tracking
- Reclamation management
- Dashboard statistics
- Archive and search
- MySQL database integration
- Swing graphical interface

## Technologies Used

- Java 21
- Maven
- MySQL 8
- Swing

## Project Structure

```text
src/main/java/com/university/handicap/
├── controllers/
├── dao/
├── models/
├── services/
├── views/
└── Main.java
```

## How to Run

1. Clone the repository.
2. Create the MySQL database.
3. Import the SQL script from the `database` folder.
4. Update the database connection settings if needed.
5. Run the project with Maven or from your IDE.

```bash
mvn clean compile
mvn exec:java
```

## Academic Context

This project was created as part of a university Java project.
