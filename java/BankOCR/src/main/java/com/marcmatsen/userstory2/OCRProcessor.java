package com.marcmatsen.userstory2;

import java.util.ArrayList;
import java.util.List;

public class OCRProcessor {

    // convert a 4 line OCR account number to a list of strings
    // where the string is concatenation of first 3 characters x 4 lines of each scanned digit
    private List<String> entryLinesToDigitStrings(List<String> entry) {
        List<String> digitStrings = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            String digitString = entry.get(0).substring(i * 3, i * 3 + 3) +
                    entry.get(1).substring(i * 3, i * 3 + 3) +
                    entry.get(2).substring(i * 3, i * 3 + 3);
            digitStrings.add(digitString);
        }
        return digitStrings;
    }

    // parse a list of 9 strings representing 3x4 grid of characters to a digit
    private List<SevenSegDigit> parseDigitStringsToDigits(List<String> entry) {
        List<SevenSegDigit> digits = new ArrayList<>();
        for (String digitString : entry) {
            digits.add(SevenSegDigit.parse(digitString));
        }
        return digits;
    }

    // batch the input data into a list of 4-line lists
    private List<List<String>> splitDataToEntryLineList(List<String> inputData) {
        List<List<String>> data = new ArrayList<>();
        for (int i = 0; i < inputData.size() / 4; i++) {
            data.add(inputData.subList(i * 4, i * 4 + 4));
        }
        return data;
    }

    List<Account> dataToAccounts(List<String> data) {

        if (data.size() % 4 != 0) {
            throw new IllegalArgumentException("Input data is expected in multiples of 4 lines");
        }

        return splitDataToEntryLineList(data)
                .stream()
                .map(this::entryLinesToDigitStrings)
                .map(this::parseDigitStringsToDigits)
                .map(Account::fromDigits)
                .toList();
    }
}
