package com.university.library.repository;

import com.university.library.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByStudentId(Long studentId);

    List<Loan> findByReturnedFalse();

    List<Loan> findByStudentIdAndReturnedFalse(Long studentId);

    long countByReturnedFalse();

    @Query("SELECT l FROM Loan l WHERE l.book.id = :bookId AND l.returned = false")
    List<Loan> findActiveLoansByBook(@Param("bookId") Long bookId);
}
