package com.university.library.config;

import com.university.library.entity.Book;
import com.university.library.entity.Student;
import com.university.library.repository.BookRepository;
import com.university.library.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final StudentRepository studentRepository;

    @Override
    public void run(String... args) {
        if (bookRepository.count() == 0) {
            bookRepository.save(new Book(null, "Le Petit Prince", "Antoine de Saint-Exupéry", "9782070408504", 1943, true, null));
            bookRepository.save(new Book(null, "1984", "George Orwell", "9782070368228", 1949, true, null));
            bookRepository.save(new Book(null, "Clean Code", "Robert C. Martin", "9780132350884", 2008, true, null));
        }
        if (studentRepository.count() == 0) {
            studentRepository.save(new Student(null, "Adam", "Dupont", "adam.dupont@efrei.net", "E2024001", null));
            studentRepository.save(new Student(null, "Julie", "Martin", "julie.martin@efrei.net", "E2024002", null));
        }
    }
}
