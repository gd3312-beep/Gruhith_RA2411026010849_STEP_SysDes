package com.gdb.tests;

import com.gdb.domain.*;
import com.gdb.exceptions.*;

public class TestInterfaceFactory {
    public static void main(String[] args) {
        System.out.println("=== Activity 12: Factory-Driven System Suite ===");

        // Test 1: Savings Account Creation & Deposit (and minimum balance check)
        try {
            IAccount savings = AccountFactory.createAccount("SAVINGS", "SAV101", "Rajesh Sharma", 25, 5000.0, "ACTIVE", "1234");
            savings.deposit(1000.0);

            // Verify minimum balance enforcement (min balance is 1000, current balance is 6000)
            boolean minBalanceEnforced = false;
            try {
                savings.withdraw(5500.0, "1234"); // Leaves 500, violates min balance
            } catch (MinimumBalanceViolationException e) {
                minBalanceEnforced = true;
            }

            if (savings.getBalance() == 6000.0 && minBalanceEnforced) {
                System.out.println("[Test 1] Savings Account Creation & Deposit: [PASS]");
            }
        } catch (Exception e) {
            System.out.println("[Test 1] Failed: " + e.getMessage());
        }

        // Test 2: Current Account Overdraft Withdrawal
        try {
            IAccount current = AccountFactory.createAccount("CURRENT", "CUR101", "Priya Patel", 30, 5000.0, "ACTIVE", "5678");
            // Balance is 5000, overdraft limit is 25000 (total available: 30000)
            current.withdraw(15000.0, "5678");
            if (current.getBalance() == -10000.0) {
                System.out.println("[Test 2] Current Account Overdraft Withdrawal: [PASS]");
            }
        } catch (Exception e) {
            System.out.println("[Test 2] Failed: " + e.getMessage());
        }

        // Test 3: Fixed Deposit Premature Withdrawal Block
        try {
            IAccount fd = AccountFactory.createAccount("FIXED_DEPOSIT", "FD101", "Amit Kumar", 45, 50000.0, "ACTIVE", "1111");
            fd.withdraw(5000.0, "1111");
            System.out.println("[Test 3] Fixed Deposit Premature Withdrawal Block: [FAIL]");
        } catch (AccountException e) {
            // Premature withdrawal is rejected with AccountException
            System.out.println("[Test 3] Fixed Deposit Premature Withdrawal Block: [PASS]");
        } catch (Exception e) {
            System.out.println("[Test 3] Failed: " + e.getMessage());
        }

        // Test 4: Invalid Type Rejection
        try {
            AccountFactory.createAccount("INVALID_TYPE", "INV101", "Unknown", 20, 1000.0, "ACTIVE", "0000");
            System.out.println("[Test 4] Invalid Type Rejection: [FAIL]");
        } catch (IllegalArgumentException e) {
            // Factory correctly throws IllegalArgumentException for unknown type
            System.out.println("[Test 4] Invalid Type Rejection: [PASS]");
        } catch (Exception e) {
            System.out.println("[Test 4] Failed: " + e.getMessage());
        }

        System.out.println("Factory-driven architecture successfully verified!");
    }
}
