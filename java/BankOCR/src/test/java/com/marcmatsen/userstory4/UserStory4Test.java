package com.marcmatsen.userstory4;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserStory4Test {

    @Test
    void test_ReadAmbiguousAccountsAndWriteResults(@TempDir Path tempDir) throws Exception {

        //given
        String sourceFile = "src/test/resources/userstory4/fileWithAmbiguousAccounts.txt";
        String outputFile = tempDir.resolve("outputFile.txt").toFile().getPath();
        
        //when
        UserStory4.processScannedAccountsToNewFile(sourceFile, outputFile);

        //then
        List<String> results = Files.readAllLines(Path.of(outputFile));
        assertEquals(12, results.size());
        assertEquals("711111111", results.get(0) );
        assertEquals("777777177", results.get(1) );
        assertEquals("200800000", results.get(2) );
        assertEquals("333393333", results.get(3) );
        assertEquals("888888888 AMB ['888886888', '888888880', '888888988']", results.get(4) );
        assertEquals("555555555 AMB ['555655555', '559555555']", results.get(5) );
        assertEquals("666666666 AMB ['666566666', '686666666']", results.get(6) );
        assertEquals("999999999 AMB ['899999999', '993999999', '999959999']", results.get(7) );
        assertEquals("490067715 AMB ['490067115', '490067719', '490867715']", results.get(8) );
        assertEquals("123456789", results.get(9) );
        assertEquals("000000051", results.get(10) );
        assertEquals("490867715", results.get(11) );
    }
}