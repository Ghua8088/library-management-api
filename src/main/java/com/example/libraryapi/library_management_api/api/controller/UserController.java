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

import com.example.libraryapi.library_management_api.api.model.User;
import com.example.libraryapi.library_management_api.service.UserService; 

@RestController
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }
    @GetMapping("/user")
    public ResponseEntity<User> getUser(@RequestParam Integer regno){
        System.out.println("GET method called with regno: " + regno);
        Optional<User> user=userService.getUser(regno);
        if(user.isPresent()){
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping("/user")
    public ResponseEntity<User> CreateUser(@RequestBody User user){
        Boolean created=userService.CreateUser(user);
        if (created) {
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    }
    @PutMapping("/user")
    public ResponseEntity<User> UpdateUser(@RequestParam Integer regno,@RequestBody User user){
        Boolean updated=userService.UpdateUser(regno,user);
        if (updated) {
            return getUser(regno);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    }
    @DeleteMapping("/user")
    public ResponseEntity<User> DeleteUser(@RequestParam Integer regno){
        ResponseEntity<User> Dlttuple=getUser(regno);
        Boolean deleted=userService.DeleteUser(regno);
        if (deleted) {
            return Dlttuple;
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    }
    @GetMapping("/all")
    public ResponseEntity<List<User>> getall(){
        Optional<List<User>> users=userService.getall();
        if(users.isPresent()){
            return ResponseEntity.ok(users.get());
        }
        return ResponseEntity.notFound().build();
    }
}
