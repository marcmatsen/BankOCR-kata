package com.marcmatsen.userstory4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ChecksumTest {
    
    @Test
    void isValid() {
        assertTrue(Checksum.isValid(345882865));
        assertFalse(Checksum.isValid(123456780));
    }

}