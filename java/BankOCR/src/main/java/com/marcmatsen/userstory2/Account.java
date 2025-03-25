package com.marcmatsen.userstory2;

import java.util.List;

public class Account {

    public final List<SevenSegDigit> digits;

    public int getAccountNumber() {
        return accountNumber;
    }

    public List<SevenSegDigit> getDigits() {
        return digits;
    }

    public final int accountNumber;

    private Account(List<SevenSegDigit> digits, int accountNumber) {
        this.digits = digits;
        this.accountNumber = accountNumber;
    }

    public static Account fromDigits(List<SevenSegDigit> digits) {
        int place = 1;
        int accountNumber = 0;
        List<SevenSegDigit> reversed = digits.reversed();
        for (int i = 1; i < 10; i++) {
            accountNumber += reversed.get(i - 1).digit.value * place;
            place *= 10;
        }
        return new Account(digits, accountNumber);
    }
}
