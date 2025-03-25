package com.marcmatsen.userstory1;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserStory1Test {

    @Test
    void getAccountNumbersFromFile() throws Exception {

        String fileName = "src/test/resources/userStory1/testFile.txt";

        List<Integer> accountNumbers = UserStory1.getAccountNumbersFromFile(fileName);

        assertEquals(3, accountNumbers.size());
        assertEquals(123456789, accountNumbers.get(0).intValue());
        assertEquals(222222222, accountNumbers.get(1).intValue());
        assertEquals(0, accountNumbers.get(2).intValue());

    }

}