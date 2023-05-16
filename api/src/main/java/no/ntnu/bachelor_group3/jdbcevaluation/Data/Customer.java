package no.ntnu.bachelor_group3.jdbcevaluation.Data;

import java.sql.*;

public class Customer {
    private Long id;
    private String name;
    private String address;
    private String zipCode;

    // Constructor
    public Customer(Long id, String name, String address, String zipCode) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.zipCode = zipCode;
    }

    // Getters and setters for each field
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}