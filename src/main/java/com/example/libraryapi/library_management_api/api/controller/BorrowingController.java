package com.example.libraryapi.library_management_api.api.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.libraryapi.library_management_api.api.model.Book;
import com.example.libraryapi.library_management_api.api.model.Borrowing;
import com.example.libraryapi.library_management_api.api.model.User;
import com.example.libraryapi.library_management_api.service.BorrowingService;

@RestController
public class BorrowingController {

    private final BorrowingService borrowingService;
    @Autowired
    public BorrowingController(BorrowingService borrowingService) {
        this.borrowingService = borrowingService;
    }
    @PostMapping("/borrowing")
    public ResponseEntity<String> createBorrowing(@RequestBody Borrowing borrowing) {
        boolean isCreated = borrowingService.createBorrowing(borrowing);
        if (isCreated) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Borrowing record created successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating borrowing record.");
        }
    }

    @GetMapping("/borrowing/all")
    public ResponseEntity<List<Borrowing>> getAllBorrowings() {
        List<Borrowing> borrowings=borrowingService.getAllBorrowings().get();
        return ResponseEntity.ok(borrowings);
    }

    @GetMapping("/borrowing")
    public ResponseEntity<HashMap<User,ArrayList<Book>>> getBorrowing(@RequestParam int regno) {
        HashMap<User,ArrayList<Book>> borrowing= borrowingService.getBorrowing(regno).get();
        if (borrowing != null) {
            return ResponseEntity.ok(borrowing);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/borrowing")
    public ResponseEntity<String> deleteBorrowing(@RequestParam int regno, @RequestParam int bookId) {
        boolean isDeleted = borrowingService.deleteBorrowing(regno, bookId);
        if (isDeleted) {
            return ResponseEntity.ok("borrowing record deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Borrowing record not found.");
        }
    }
}
