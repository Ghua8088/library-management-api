package com.example.libraryapi.library_management_api.api.model;
public class User{
    private int regno;
    private String name;
    private String email;
    public User(int id, String name, String email) {
        this.regno = id;
        this.name = name;
        this.email = email;
    }
    public User(){
        regno=0;
        name="";
        email="";
    }
    public int getRegno(){
        return regno;
    }
    public void setRegno(int regno) {
        this.regno = regno;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public  String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }
    @Override
    public String toString(){
        return " \n name: " + name + "\nemail: " + email+"\n regno: " + regno;
    }

}