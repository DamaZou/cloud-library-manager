package com.university.library.service;

import com.university.library.entity.Book;
import com.university.library.entity.Loan;
import com.university.library.entity.Student;
import com.university.library.exception.BadRequestException;
import com.university.library.exception.ResourceNotFoundException;
import com.university.library.repository.BookRepository;
import com.university.library.repository.LoanRepository;
import com.university.library.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final StudentRepository studentRepository;

    private static final int LOAN_DURATION_DAYS = 14;

    @Transactional
    public Loan createLoan(Long bookId, Long studentId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Livre introuvable avec l'id : " + bookId));
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Étudiant introuvable avec l'id : " + studentId));

        if (!Boolean.TRUE.equals(book.getAvailable())) {
            throw new BadRequestException("Le livre '" + book.getTitle() + "' n'est pas disponible actuellement.");
        }

        Loan loan = new Loan();
        loan.setBook(book);
        loan.setStudent(student);
        loan.setLoanDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusDays(LOAN_DURATION_DAYS));
        loan.setReturned(false);

        book.setAvailable(false);
        bookRepository.save(book);

        return loanRepository.save(loan);
    }

    @Transactional
    public Loan returnLoan(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Emprunt introuvable avec l'id : " + loanId));

        if (Boolean.TRUE.equals(loan.getReturned())) {
            throw new BadRequestException("Ce livre a déjà été retourné.");
        }

        loan.setReturned(true);
        loan.setReturnDate(LocalDate.now());

        Book book = loan.getBook();
        book.setAvailable(true);
        bookRepository.save(book);

        return loanRepository.save(loan);
    }

    public void deleteLoan(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Emprunt introuvable avec l'id : " + loanId));
        if (!Boolean.TRUE.equals(loan.getReturned())) {
            Book book = loan.getBook();
            book.setAvailable(true);
            bookRepository.save(book);
        }
        loanRepository.delete(loan);
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public List<Loan> getCurrentLoans() {
        return loanRepository.findByReturnedFalse();
    }

    public List<Loan> getLoanHistoryByStudent(Long studentId) {
        return loanRepository.findByStudentId(studentId);
    }

    public long countActiveLoans() {
        return loanRepository.countByReturnedFalse();
    }

    public long countTotalLoans() {
        return loanRepository.count();
    }
}
