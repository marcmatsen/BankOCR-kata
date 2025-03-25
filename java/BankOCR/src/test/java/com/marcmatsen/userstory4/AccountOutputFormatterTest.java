package com.marcmatsen.userstory4;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.marcmatsen.userstory4.Digit.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountOutputFormatterTest {

    AccountOutputFormatter formatter = new AccountOutputFormatter();

    // 457508000
    List<Digit> validAccountDigits = List.of(
            FOUR,
            FIVE,
            SEVEN,
            FIVE,
            ZERO,
            EIGHT,
            ZERO,
            ZERO,
            ZERO
    );
    
    @Test
    void test_formatAccountForFileOutput_ValidAccount_ReturnsFormattedString() {

        //given
        List<String> digitStrings = this.validAccountDigits.stream()
                .map(Digit::getGrid)
                .toList();
        List<SevenSegDigit> scannedDigits = digitStrings.stream().map(SevenSegDigit::parse).toList();
        ScannedAccount account = ScannedAccount.fromScannedDigitsWithAmbiguity(scannedDigits);
        
        //when
        String result = formatter.formatAccountForFileOutput(account);
        
        //then
        assertEquals("457508000", result);
    }
    
    @Test
    void test_formatAccountForFileOutput_InvalidAccount_ReturnsFormattedString() {
        
        //given
        
        List<Digit> invalidAccountDigits = new ArrayList<>(this.validAccountDigits);
        invalidAccountDigits.set(8, TWO);
        
        List<String> digitStrings = invalidAccountDigits.stream()
                .map(Digit::getGrid)
                .toList();
        List<SevenSegDigit> scannedDigits = digitStrings.stream().map(SevenSegDigit::parse).toList();
        ScannedAccount account = ScannedAccount.fromScannedDigitsWithAmbiguity(scannedDigits);

        //when
        String result = formatter.formatAccountForFileOutput(account);

        //then
        assertEquals("457508002 ERR", result);
    }

    @Test
    void test_formatAccountForFileOutput_IllegibleAccount_ReturnsFormattedString() {

        //given
        List<String> digitStrings = this.validAccountDigits.stream()
                .map(Digit::getGrid)
                .collect(Collectors.toList());
        digitStrings.set(0, "   | |   ");
        List<SevenSegDigit> scannedDigits = digitStrings.stream().map(SevenSegDigit::parse).toList();
        ScannedAccount account = ScannedAccount.fromScannedDigitsWithAmbiguity(scannedDigits);

        //when
        String result = formatter.formatAccountForFileOutput(account);

        //then
        assertEquals("?57508000 ILL", result);
    }

    @Test
    void test_formatAccountForFileOutput_AmbiguousAccount_ReturnsFormattedString_withSortedAccountNumbers() {

        //given
        List<String> digitStrings = this.validAccountDigits.stream()
                .map(Digit::getGrid)
                .toList();
        List<SevenSegDigit> scannedDigits = digitStrings.stream().map(SevenSegDigit::parse).toList();
        ScannedAccount account = new AmbiguousAccount(scannedDigits, List.of(234567890, 123456789));

        //when
        String result = formatter.formatAccountForFileOutput(account);

        //then
        assertEquals("457508000 AMB ['123456789', '234567890']", result);
    }

}