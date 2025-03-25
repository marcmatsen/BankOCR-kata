package com.marcmatsen.userstory4;

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
                
                 _  _  _  _  _  _  _  _  _\s
                |_||_||_||_||_||_||_||_||_|
                 _| _| _| _| _| _| _| _| _|
                \n
                """;

        List<String> testDataLines = new ArrayList<>();
        Collections.addAll(testDataLines, testData.split("\n"));
        testDataLines.add("");

        //when
        List<ScannedAccount> accounts = ocrProcessor.OCRDataToAccounts(testDataLines);

        //then
        assertEquals(5, accounts.size());
        
        assertInstanceOf(ValidAccount.class, accounts.get(0));
        assertInstanceOf(IllegibleAccount.class, accounts.get(1));
        assertInstanceOf(IllegibleAccount.class, accounts.get(2));
        // new behavior in us4 means formerly invalid account for this data now valid
        assertInstanceOf(ValidAccount.class, accounts.get(3));
        assertInstanceOf(AmbiguousAccount.class, accounts.get(4));
        
        ValidAccount validAccount = (ValidAccount) accounts.get(0);
        assertEquals(51, validAccount.getAccountNumber());
        
        AmbiguousAccount ambiguousAccount = (AmbiguousAccount) accounts.get(4);
        assertEquals(899999999, ambiguousAccount.getPossibleAccountNumbers().get(0));
    }

    @Test
    void test_OCRDataToAccounts_ValidData_AmbiguousAccount() {

        //given
        //test data with 4 account numbers

        String testData = """
                 _  _  _  _  _  _  _  _  _\s
                |_||_||_||_||_||_||_||_||_|
                 _| _| _| _| _| _| _| _| _|
                \n
                """;

        List<String> testDataLines = new ArrayList<>();
        Collections.addAll(testDataLines, testData.split("\n"));
        testDataLines.add("");

        //when
        List<ScannedAccount> accounts = ocrProcessor.OCRDataToAccounts(testDataLines);

        //then
        assertEquals(1, accounts.size());
        
        assertInstanceOf(AmbiguousAccount.class, accounts.get(0));

        AmbiguousAccount ambiguousAccount = (AmbiguousAccount) accounts.get(0);
        assertEquals(3, ambiguousAccount.getPossibleAccountNumbers().size());
        assertEquals(899999999, ambiguousAccount.getPossibleAccountNumbers().get(0));
        assertEquals(993999999, ambiguousAccount.getPossibleAccountNumbers().get(1));
        assertEquals(999959999, ambiguousAccount.getPossibleAccountNumbers().get(2));
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