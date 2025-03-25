package com.marcmatsen.userstory2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChecksumTest {
    
    @Test
    void isValid() {
        assertTrue(Checksum.isValid(345882865));
        assertFalse(Checksum.isValid(123456780));
    }

}