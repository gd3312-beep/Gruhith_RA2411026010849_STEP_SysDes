package com.gdb.domain;

import java.util.HashMap;
import java.util.Map;

// Rules engine to calculate banking limits and rates based on customer tenure
public class AccountRulesEngine {
    // In-memory lookup tables for savings account rules
    private static final Map<String, Double> SAVINGS_MIN_BALANCES = new HashMap<>();
    private static final Map<String, Double> SAVINGS_INTEREST_RATES = new HashMap<>();

    static {
        // Initialize minimum balance requirements per tenure tier
        SAVINGS_MIN_BALANCES.put("NEW", 10000.0);
        SAVINGS_MIN_BALANCES.put("STANDARD", 7500.0);
        SAVINGS_MIN_BALANCES.put("PREMIUM", 5000.0);
        SAVINGS_MIN_BALANCES.put("PRIVILEGE", 2500.0);

        // Initialize interest rates per tenure tier
        SAVINGS_INTEREST_RATES.put("NEW", 2.70);
        SAVINGS_INTEREST_RATES.put("STANDARD", 3.00);
        SAVINGS_INTEREST_RATES.put("PREMIUM", 3.50);
        SAVINGS_INTEREST_RATES.put("PRIVILEGE", 4.00);
    }

    // Maps customer tenure in years to a tier bucket
    public static String getSavingsBucket(int tenureYears) {
        if (tenureYears >= 5) {
            return "PRIVILEGE";
        } else if (tenureYears >= 3) {
            return "PREMIUM";
        } else if (tenureYears >= 1) {
            return "STANDARD";
        } else {
            return "NEW";
        }
    }

    // Returns required minimum balance based on tenure
    public static double getSavingsMinBalance(int tenureYears) {
        String bucket = getSavingsBucket(tenureYears);
        return SAVINGS_MIN_BALANCES.getOrDefault(bucket, 10000.0);
    }

    // Returns annual interest rate based on tenure
    public static double getSavingsInterestRate(int tenureYears) {
        String bucket = getSavingsBucket(tenureYears);
        return SAVINGS_INTEREST_RATES.getOrDefault(bucket, 2.70);
    }

    // Calculates overdraft limit as 2.5x monthly turnover, minimum Rs 25,000
    public static double getCurrentOverdraftLimit(double monthlyTurnover) {
        return Math.max(25000.0, monthlyTurnover * 2.5);
    }

    // Returns fixed deposit interest rate based on duration in months
    public static double getFDInterestRate(int months) {
        if (months >= 36) {
            return 7.50;
        } else if (months >= 12) {
            return 6.50;
        } else {
            return 5.00;
        }
    }
}
