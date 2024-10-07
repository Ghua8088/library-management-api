package com.example.libraryapi.library_management_api.api.model;

public class Borrowing {
    private int regno;
    private int bookid;

    public Borrowing(int regno, int bookid) {
        this.regno = regno;
        this.bookid = bookid;
    }
    public Borrowing() {}
    public int getRegno() {
        return regno;
    }
    public void setRegno(int regno) {
        this.regno = regno;
    }
    public int getBookid() {
        return bookid;
    }
    public void setBookid(int bookid) {
        this.bookid = bookid;
    }
    @Override
    public String toString() {
        return "Borrowing{regno=" + regno + ", bookid=" + bookid + "}";
    }
}

