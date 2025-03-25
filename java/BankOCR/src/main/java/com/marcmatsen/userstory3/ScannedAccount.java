package com.marcmatsen.userstory3;

import java.util.List;

public sealed class ScannedAccount
    permits InvalidAccount, IllegibleAccount, ValidAccount {

    private final List<SevenSegDigit> digits;
    
    public ScannedAccount(List<SevenSegDigit> digits) {
        this.digits = digits;
    }

    public List<SevenSegDigit> getDigits() {
        return digits;
    }

    public static ScannedAccount fromScannedDigits(List<SevenSegDigit> digits) {
        List<ValidDigit> validDigits = digits
                .stream()
                .filter(d -> d instanceof ValidDigit)
                .map(d -> (ValidDigit) d)
                .toList();
        if (validDigits.size() < 9) {
            return new IllegibleAccount(digits);
        }
        int place = 1;
        int accountNumber = 0;
        
        List<ValidDigit> reversed = validDigits.reversed();
        for (int i = 1; i < 10; i++) {
            accountNumber += reversed.get(i - 1).getDigit().getValue() * place;
            place *= 10;
        }
        
        if (Checksum.isValid(accountNumber)) {
            return new ValidAccount(digits, accountNumber);
        } else {
            return new InvalidAccount(digits, accountNumber);
        }
    }
}

final class ValidAccount extends ScannedAccount {

    public int getAccountNumber() {
        return accountNumber;
    }

    private final int accountNumber;
    
    ValidAccount(List<SevenSegDigit> digits, int accountNumber) {
        super(digits);
        this.accountNumber = accountNumber;
    }
}

final class InvalidAccount extends ScannedAccount {

    public int getAccountNumber() {
        return accountNumber;
    }

    private final int accountNumber;
    
    InvalidAccount(List<SevenSegDigit> digits, int accountNumber) {
        super(digits);
        this.accountNumber = accountNumber;
    }
}

final class IllegibleAccount extends ScannedAccount {
    
    IllegibleAccount(List<SevenSegDigit> digits) {
        super(digits);
    }
}
