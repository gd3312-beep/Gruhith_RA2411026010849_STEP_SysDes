package com.gdb.domain;

import com.gdb.exceptions.*;

// Common interface defining core banking operations for all account types
public interface IAccount {
    String getAccountNumber();
    String getName();
    int getAge();
    double getBalance();
    String getAccountType();
    String getStatus();

    boolean validatePin(String enteredPin);
    boolean changePin(String oldPin, String newPin);

    void deposit(double amount) throws InvalidAmountException;
    void withdraw(double amount, String enteredPin) throws AccountException;
    void displayAccountInfo();
}

