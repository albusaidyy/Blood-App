package com.example.bloodapp.model;


import java.io.Serializable;

public class User implements Serializable {

    private int id;
    private String email;
    private String password;
    private String userType;

    private String name;
    private String address;
    private String contact;
    private String gender;
    private String bloodType;
    private String status;


    public User() {

    }

    public User(int id, String email, String password, String userType, String name, String address, String contact, String gender, String bloodType, String status) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.userType = userType;

        this.name = name;
        this.address = address;
        this.contact = contact;
        this.gender = gender;
        this.bloodType = bloodType;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}