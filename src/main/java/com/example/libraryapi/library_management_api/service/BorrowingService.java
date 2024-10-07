package com.example.libraryapi.library_management_api.service;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.libraryapi.library_management_api.api.model.Book;
import com.example.libraryapi.library_management_api.api.model.Borrowing;
import com.example.libraryapi.library_management_api.api.model.User;
@Service
public class BorrowingService {
    private final DBCredentials dbCredentials;
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;
    @Autowired
    public BorrowingService(DBCredentials dbCredentials) {
        this.dbCredentials = dbCredentials;
    }
    public boolean createBorrowing(Borrowing borrowing) {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            try (Connection connection = DriverManager.getConnection(dbCredentials.getUrl(),dbCredentials.getProperties())) {
                String sql = "INSERT INTO borrowings (regno, bookid) VALUES (?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1, borrowing.getRegno());
                    statement.setInt(2, borrowing.getBookid());
                    statement.executeUpdate();
                    return true;
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public Optional<List<Borrowing>> getAllBorrowings() {
        List<Borrowing> borrowings = new ArrayList<>();
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            try (Connection connection = DriverManager.getConnection(dbCredentials.getUrl(),dbCredentials.getProperties())) {
                String sql = "SELECT regno, bookid FROM borrowings";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    ResultSet resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        Borrowing borrowing = new Borrowing();
                        borrowing.setRegno(resultSet.getInt("regno"));
                        borrowing.setBookid(resultSet.getInt("bookid"));
                        borrowings.add(borrowing);
                    }
                    return Optional.of(borrowings);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
        return Optional.empty();
    }

    public Optional<HashMap<User,ArrayList<Book>>> getBorrowing(int regno){
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            try (Connection connection = DriverManager.getConnection(dbCredentials.getUrl(),dbCredentials.getProperties())) {
                String sql = "SELECT bookid FROM borrowings WHERE regno = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1, regno);
                    ResultSet resultSet = statement.executeQuery();
                    ArrayList<Book> opList=new ArrayList<>();
                    while(resultSet.next()) {
                        Book bookDetails = bookService.getBook(resultSet.getInt("bookid")).get();
                        if(bookDetails != null){
                            opList.add(bookDetails);
                        }else{
                            return Optional.empty();
                        }
                        
                    }
                    User userDetails = userService.getUser(regno).get();
                    HashMap<User,ArrayList<Book>> op=new HashMap<>();
                    op.put(userDetails, opList);
                    return Optional.of(op);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
        return Optional.empty();
    }

    public boolean deleteBorrowing(int regno, int bookid) {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            try (Connection connection = DriverManager.getConnection(dbCredentials.getUrl(),dbCredentials.getProperties())) {
                String sql = "DELETE FROM borrowings WHERE regno = ? AND bookid = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1, regno);
                    statement.setInt(2, bookid);
                    int rowsDeleted = statement.executeUpdate();
                    return rowsDeleted > 0;
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
        return false;
    }
}
