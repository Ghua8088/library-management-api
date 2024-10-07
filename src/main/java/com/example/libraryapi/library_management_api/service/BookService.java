package com.example.libraryapi.library_management_api.service;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import org.springframework.stereotype.Service;
import com.example.libraryapi.library_management_api.api.model.Book;
@Service
public class BookService {
    private final Properties props = new Properties();
    public BookService(){
        props.setProperty("user", "SYS");
        props.setProperty("password", "Raghushree2005");
        props.setProperty("internal_logon", "SYSDBA");    
    }
    public Optional<Book> getBook(int BookId){
        Optional optional=Optional.empty();
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl",props)) {
                System.out.println("Connected to the database");
                String sql = "SELECT * FROM books WHERE bookid = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1, BookId);
                    ResultSet resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        Book book = new Book();
                        book.setBookId(resultSet.getInt("bookid"));
                        book.setName(resultSet.getString("name"));
                        book.setStock(resultSet.getInt("stock"));
                        System.out.println(book);
                        optional=Optional.of(book);
                        return optional;
                    }
                }
            }
            System.out.println("Connected to database");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
        return optional;
    }
    public Boolean CreateBook(Book book){
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl",props)) {
                System.out.println("Connected to the database");
                String sql = "Insert into books values(?,?,?)";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1, book.getBookId());
                    statement.setString(2, book.getName());
                    statement.setInt(3, book.getStock());
                    statement.executeUpdate();
                    
                    return true;
                    
                }
                
            }
            
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }

        return false;
    }
    public Boolean UpdateBook(Integer BookId, Book book) {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl",props)) {
                System.out.println("Connected to the database");
                String sql = "update books set bookid=?,name=?,stock=? where bookid=?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1, book.getBookId());
                    statement.setString(2, book.getName());
                    statement.setInt(3, book.getStock());
                    statement.setInt(4, BookId);
                    int rowsUpdated = statement.executeUpdate();
                    return rowsUpdated>0;
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
        return false;
    }
    public Boolean DeleteBook(int regno) {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl",props)) {
                System.out.println("Connected to the database");
                String sql = "delete from books where bookid=?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1,regno);
                    int rowsUpdated = statement.executeUpdate();
                    return rowsUpdated>0;
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
        return false;
    }
    public Optional<List<Book>> getall() {
        ArrayList<Book> users = new ArrayList<Book>();
        Optional optional = Optional.empty();
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl",props)) {
                System.out.println("Connected to the database"); 
                String sql = "SELECT * FROM books";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    ResultSet resultSet = statement.executeQuery();
                    while(resultSet.next()) {
                        Book book = new Book();
                        book.setBookId(resultSet.getInt("BookId"));
                        book.setName(resultSet.getString("Name"));
                        book.setStock(resultSet.getInt("Stock"));
                        System.out.println(book);
                        users.add(book);
                    }
                    optional=Optional.of(users);
                    return optional;
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
        return optional;
    } 
}
