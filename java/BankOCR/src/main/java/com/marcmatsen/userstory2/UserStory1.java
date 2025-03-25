package com.marcmatsen.userstory2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class UserStory1 {

    public static List<Integer> getAccountNumbersFromFile(String fileName) throws IOException {
        OCRProcessor ocrProcessor = new OCRProcessor();
        List<String> fileData = Files.readAllLines(Path.of(fileName));

        //A full blank line is expected which is truncated when file is read
//        List<String> data = new ArrayList<>();
//        data.addAll(fileData);
//        data.add("\n");

        return ocrProcessor
                .dataToAccounts(fileData)
                .stream()
                .map(Account::getAccountNumber)
                .toList();
    }
}