package com.university.library.controller;

import com.university.library.entity.Loan;
import com.university.library.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
@Tag(name = "Loans", description = "Gestion des emprunts")
public class LoanController {

    private final LoanService loanService;

    @PostMapping
    @Operation(summary = "Enregistrer un emprunt (body: {\"bookId\":1,\"studentId\":1})")
    public ResponseEntity<Loan> createLoan(@RequestBody Map<String, Long> payload) {
        Long bookId = payload.get("bookId");
        Long studentId = payload.get("studentId");
        return new ResponseEntity<>(loanService.createLoan(bookId, studentId), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/return")
    @Operation(summary = "Retourner un livre emprunté")
    public ResponseEntity<Loan> returnLoan(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.returnLoan(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un emprunt")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long id) {
        loanService.deleteLoan(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Lister tous les emprunts")
    public List<Loan> getAllLoans() {
        return loanService.getAllLoans();
    }

    @GetMapping("/current")
    @Operation(summary = "Lister les emprunts en cours")
    public List<Loan> getCurrentLoans() {
        return loanService.getCurrentLoans();
    }

    @GetMapping("/student/{studentId}")
    @Operation(summary = "Historique des emprunts d'un étudiant")
    public List<Loan> getLoanHistoryByStudent(@PathVariable Long studentId) {
        return loanService.getLoanHistoryByStudent(studentId);
    }

    @GetMapping("/count/active")
    @Operation(summary = "Nombre total de livres actuellement empruntés")
    public Map<String, Long> countActiveLoans() {
        return Map.of("activeLoans", loanService.countActiveLoans());
    }

    @GetMapping("/count/total")
    @Operation(summary = "Nombre total d'emprunts (historique inclus)")
    public Map<String, Long> countTotalLoans() {
        return Map.of("totalLoans", loanService.countTotalLoans());
    }
}
