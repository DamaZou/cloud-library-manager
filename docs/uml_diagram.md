# Diagramme UML des entités — Cloud Library Manager

```mermaid
classDiagram
    class Book {
        +Long id
        +String title
        +String author
        +String isbn
        +Integer publicationYear
        +Boolean available
    }

    class Student {
        +Long id
        +String firstName
        +String lastName
        +String email
        +String studentNumber
    }

    class Loan {
        +Long id
        +LocalDate loanDate
        +LocalDate dueDate
        +LocalDate returnDate
        +Boolean returned
    }

    Student "1" --> "0..*" Loan : effectue
    Book "1" --> "0..*" Loan : concerne
    Loan "0..*" --> "1" Book
    Loan "0..*" --> "1" Student
```

## Relations
- **Student → Loan** : One-To-Many (`@OneToMany(mappedBy = "student")`) — un étudiant peut avoir plusieurs emprunts.
- **Book → Loan** : One-To-Many (`@OneToMany(mappedBy = "book")`) — un livre peut être emprunté plusieurs fois au cours de son cycle de vie.
- **Loan → Book / Student** : Many-To-One (`@ManyToOne`) — un emprunt concerne un seul livre et un seul étudiant.
