package com.example.libraryapi.library_management_api.api.model;

public class Book {
    private int bookId;
    private String Name;
    private int Stock;
    public Book(int bookId,String Name,int stock){
        this.bookId = bookId;
        this.Name =Name;
        this.Stock = stock;
    }
    public Book(){
        this.bookId = 0;
        this.Name ="";
        this.Stock = 0;
    }
    public int getBookId() {
        return bookId;
    }
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
    public String getName() {
        return Name;
    }
    public void setName(String Name) {
        this.Name = Name;
    }
    public int getStock(){
        return Stock;
    }
    public void setStock(int Stock) {
        this.Stock = Stock;
    }
}
