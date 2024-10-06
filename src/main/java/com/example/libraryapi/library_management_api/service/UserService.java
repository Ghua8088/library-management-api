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

import com.example.libraryapi.library_management_api.api.model.User;
@Service
public class UserService {
    private final Properties props = new Properties();
    public UserService(){
        props.setProperty("user", "SYS");
        props.setProperty("password", "Raghushree2005");
        props.setProperty("internal_logon", "SYSDBA");
    }
    public Optional<User> getUser(int regno){
        Optional optional=Optional.empty();
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl",props)) {
                System.out.println("Connected to the database");
                String sql = "SELECT * FROM users WHERE regno = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1, regno);
                    ResultSet resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        User user = new User();
                        user.setRegno(resultSet.getInt("regno"));
                        user.setName(resultSet.getString("name"));
                        user.setEmail(resultSet.getString("email"));
                        System.out.println(user);
                        optional=Optional.of(user);
                        return optional;
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
        return optional;
    }
    public Boolean CreateUser(User user){
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl",props)) {
                System.out.println("Connected to the database");
                String sql = "Insert into users values(?,?,?)";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1, user.getRegno());
                    statement.setString(2, user.getName());
                    statement.setString(3, user.getEmail());
                    statement.executeUpdate();
                    return true;
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }

        return false;
    }
    public Boolean UpdateUser(Integer regno, User user) {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl",props)) {
                System.out.println("Connected to the database");
                String sql = "update users set regno=?,name=?,email=? where regno=?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1, user.getRegno());
                    statement.setString(2, user.getName());
                    statement.setString(3, user.getEmail());
                    statement.setInt(4, regno);
                    int rowsUpdated = statement.executeUpdate();
                    return rowsUpdated>0;
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
        return false;
    }
    public Boolean DeleteUser(int regno) {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl",props)) {
                System.out.println("Connected to the database");
                String sql = "delete from users where regno=?";
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
    public Optional<List<User>> getall() {
        ArrayList<User> users = new ArrayList<>();
        Optional optional = Optional.empty();
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl",props)) {
                System.out.println("Connected to the database");
                String sql = "SELECT * FROM users";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    ResultSet resultSet = statement.executeQuery();
                    while(resultSet.next()) {
                        User user = new User();
                        user.setRegno(resultSet.getInt("regno"));
                        user.setName(resultSet.getString("name"));
                        user.setEmail(resultSet.getString("email"));
                        System.out.println(user);
                        users.add(user);
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