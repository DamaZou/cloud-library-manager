-- Script SQL du schéma de la base "Cloud Library Manager"
-- NB : ce schéma est généré automatiquement par Hibernate (ddl-auto=update).
-- Ce script est fourni à titre documentaire / pour un déploiement manuel.

CREATE TABLE books (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    isbn VARCHAR(50) NOT NULL UNIQUE,
    publication_year INTEGER NOT NULL,
    available BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE students (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    student_number VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE loans (
    id BIGSERIAL PRIMARY KEY,
    book_id BIGINT NOT NULL REFERENCES books(id),
    student_id BIGINT NOT NULL REFERENCES students(id),
    loan_date DATE NOT NULL,
    due_date DATE NOT NULL,
    return_date DATE,
    returned BOOLEAN NOT NULL DEFAULT FALSE
);

-- Données de test
INSERT INTO books (title, author, isbn, publication_year, available) VALUES
 ('Le Petit Prince', 'Antoine de Saint-Exupéry', '9782070408504', 1943, TRUE),
 ('1984', 'George Orwell', '9782070368228', 1949, TRUE),
 ('Clean Code', 'Robert C. Martin', '9780132350884', 2008, TRUE);

INSERT INTO students (first_name, last_name, email, student_number) VALUES
 ('Adam', 'Dupont', 'adam.dupont@efrei.net', 'E2024001'),
 ('Julie', 'Martin', 'julie.martin@efrei.net', 'E2024002');
