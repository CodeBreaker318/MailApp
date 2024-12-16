package com.example.mailapp;

/**
 * Класс для хранения информации об email.
 */
public class Email {
    private final String address;
    private final String name;

    public Email(String address, String name) {
        this.address = address;
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }
}
