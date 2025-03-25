package com.marcmatsen.userstory3;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class UserStory3 {

    public static void processScannedAccountsToNewFile(String sourceFileName, String outputFileName) throws Exception {
        OCRProcessor ocrProcessor = new OCRProcessor();
        AccountOutputFormatter accountOutputFormatter = new AccountOutputFormatter();
        List<String> fileData = Files.readAllLines(Path.of(sourceFileName));

        List<String> results = ocrProcessor
                .OCRDataToAccounts(fileData)
                .stream()
                .map(accountOutputFormatter::formatAccountForFileOutput)
                .toList();
        
        Files.write(Path.of(outputFileName), results);

    }
    
    
}
