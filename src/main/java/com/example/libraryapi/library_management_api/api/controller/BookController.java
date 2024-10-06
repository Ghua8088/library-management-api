package com.example.libraryapi.library_management_api.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.libraryapi.library_management_api.api.model.Book;
import com.example.libraryapi.library_management_api.service.BookService;
@RestController
public class BookController {
    private final BookService bookService;
    @Autowired
    public BookController(BookService bookService){
        this.bookService = bookService;
    }
    @GetMapping("/book")
    public ResponseEntity<Book> getBook(@RequestParam Integer BookId){
        System.out.println("GET method called with regno: " + BookId);
        Optional<Book> book=bookService.getBook(BookId);
        if(book.isPresent()){
            return ResponseEntity.ok(book.get());
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping("/book")
    public ResponseEntity<Book> CreateBook(@RequestBody Book book){
        Boolean created=bookService.CreateBook(book);
        if (created) {
            return ResponseEntity.status(HttpStatus.CREATED).body(book);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    }
    @PutMapping("/book")
    public ResponseEntity<Book> UpdateBook(@RequestParam Integer bookId,@RequestBody Book book){
        Boolean updated=bookService.UpdateBook(bookId,book);
        if (updated) {
            return getBook(bookId);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    }
    @DeleteMapping("/book")
    public ResponseEntity<Book> DeleteBook(@RequestParam Integer BookId){
        ResponseEntity<Book> Dlttuple=getBook(BookId);
        Boolean deleted=bookService.DeleteBook(BookId);
        if (deleted) {
            return Dlttuple;
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    }
    @GetMapping("/books")
    public ResponseEntity<List<Book>> getall(){
        Optional<List<Book>> books=bookService.getall();
        if(books.isPresent()){
            return ResponseEntity.ok(books.get());
        }
        return ResponseEntity.notFound().build();
    }
}
