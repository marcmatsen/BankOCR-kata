package com.marcmatsen.userstory1;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OCRProcessorTest {

    OCRProcessor ocrProcessor = new OCRProcessor();

    @Test
    void OCRDataToAccounts() {

        String testData = """
                 _  _  _  _  _  _  _  _  _\s
                | || || || || || || || || |
                |_||_||_||_||_||_||_||_||_|
                
                    _  _     _  _  _  _  _\s
                  | _| _||_||_ |_   ||_||_|
                  ||_  _|  | _||_|  ||_| _|
                \n
                """;

        List<String> testDataLines = new ArrayList<>();
        Collections.addAll(testDataLines, testData.split("\n"));
        testDataLines.add("");

        List<Account> accounts = ocrProcessor.dataToAccounts(testDataLines);

        assertEquals(2, accounts.size());
        assertEquals(0, accounts.get(0).accountNumber);
        assertEquals(123456789, accounts.get(1).accountNumber);
    }
}