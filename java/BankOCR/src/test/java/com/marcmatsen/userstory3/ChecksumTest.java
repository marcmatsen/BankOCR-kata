package com.marcmatsen.userstory3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ChecksumTest {
    
    @Test
    void isValid() {
        assertTrue(com.marcmatsen.userstory3.Checksum.isValid(345882865));
        assertFalse(Checksum.isValid(123456780));
    }

}