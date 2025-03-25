package com.marcmatsen.userstory1;

import java.util.Arrays;

public class SevenSegDigit {

    public enum Digit {

        ONE("     |  |",1),
        TWO(" _  _||_ ", 2),
        THREE(" _  _| _|", 3),
        FOUR("   |_|  |", 4),
        FIVE(" _ |_  _|", 5),
        SIX(" _ |_ |_|", 6),
        SEVEN(" _   |  |", 7),
        EIGHT(" _ |_||_|", 8),
        NINE(" _ |_| _|", 9),
        ZERO(" _ | ||_|", 0);

        Digit(String elements, int value) {
            this.elements = elements;
            this.value = value;
        }

        public final String elements;
        public final int value;
    }


    public String originalElements;
    public Digit digit;

    public SevenSegDigit(String originalElements, Digit digit) {
        this.originalElements = originalElements;
        this.digit = digit;
    }

    static SevenSegDigit parse(String elements) {
        Digit digit = Arrays.stream(Digit.values())
                .filter(d -> d.elements.equals(elements))
                .findFirst()
                .orElseThrow();
        return new SevenSegDigit(elements, digit);
    }

    @Override
    public String toString() {
        return originalElements.substring(0, 3) + "\n" + originalElements.substring(3, 6) + "\n" + originalElements.substring(6, 9) + "\n" + originalElements.substring(9, 12);
    }
}


