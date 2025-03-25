package com.marcmatsen.userstory3;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OCRProcessorTest {
    
    OCRProcessor ocrProcessor = new OCRProcessor();

    @Test
    void test_OCRDataToAccounts_ValidData_ReturnsListOfScannedAccounts() {

        //given
        //test data with 4 account numbers
        
        String testData = """
                 _  _  _  _  _  _  _  _   \s
                | || || || || || || ||_   |
                |_||_||_||_||_||_||_| _|  |
                
                    _  _  _  _  _  _     _\s
                |_||_|| || ||_   |  |  | _\s
                  | _||_||_||_|  |  |  | _|
                
                    _  _     _  _  _  _  _\s
                  | _| _||_| _ |_   ||_||_|
                  ||_  _|  | _||_|  ||_| _\s
                
                 _  _  _  _  _  _  _  _  _\s
                | || || || || || || ||_ | |
                |_||_||_||_||_||_||_| _||_|
                \n
                """;

        List<String> testDataLines = new ArrayList<>();
        Collections.addAll(testDataLines, testData.split("\n"));
        testDataLines.add("");

        //when
        List<ScannedAccount> accounts = ocrProcessor.OCRDataToAccounts(testDataLines);

        //then
        assertEquals(4, accounts.size());
        
        assertInstanceOf(ValidAccount.class, accounts.get(0));
        assertInstanceOf(IllegibleAccount.class, accounts.get(1));
        assertInstanceOf(IllegibleAccount.class, accounts.get(2));
        assertInstanceOf(InvalidAccount.class, accounts.get(3));
        
        ValidAccount validAccount = (ValidAccount) accounts.get(0);
        assertEquals(51, validAccount.getAccountNumber());
    }
    
    @Test
    void test_OCRDataToAccounts_invalidLineCount_throwsIllegalArgumentException() {
        
        String testData = """
                 _  _  _  _  _  _  _  _   \s
                | || || || || || || ||_   |
                |_||_||_||_||_||_||_| _|  |
                
                    _  _  _  _  _  _     _\s
                |_||_|| || ||_   |  |  | _\s
                """;

        List<String> testDataLines = new ArrayList<>();
        Collections.addAll(testDataLines, testData.split("\n"));
        testDataLines.add("");
        
        assertThrows(IllegalArgumentException.class, () -> ocrProcessor.OCRDataToAccounts(testDataLines));
    }

    @Test
    void test_OCRDataToAccounts_truncatedLines_throwsIllegalArgumentException() {

        String testData = """
                 _  _  _  _  _   \s
                | || || || ||_   |
                |_||_||_||_| _|  |
                
                """;

        List<String> testDataLines = new ArrayList<>();
        Collections.addAll(testDataLines, testData.split("\n"));
        testDataLines.add("");

        assertThrows(IllegalArgumentException.class, () -> ocrProcessor.OCRDataToAccounts(testDataLines));
    }
    
}