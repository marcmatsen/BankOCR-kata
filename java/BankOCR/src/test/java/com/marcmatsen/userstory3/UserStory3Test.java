package com.marcmatsen.userstory3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserStory3Test {
    
    @Test
    void testReadValidInvalidIllegibleAccountsAndWriteResults(@TempDir Path tempDir) throws Exception {
        
        String sourceFile = "src/test/resources/userstory3/fileWithInvalidAndIllegibleAccounts.txt";
        String outputFile = tempDir.resolve("outputFile.txt").toFile().getPath();
        
        UserStory3.processScannedAccountsToNewFile(sourceFile, outputFile);
        
        List<String> results = Files.readAllLines(Path.of(outputFile));
        
        assertEquals(4, results.size());
        assertEquals("000000051", results.get(0));
        assertEquals("49006771? ILL", results.get(1));
        assertEquals("1234?678? ILL", results.get(2));
        assertEquals("000000050 ERR", results.get(3));
    }
}