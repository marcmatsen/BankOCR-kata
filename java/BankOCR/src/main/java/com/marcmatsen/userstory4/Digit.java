package com.marcmatsen.userstory4;

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

    Digit(String grid, int value) {
        this.grid = grid;
        this.value = value;
    }

    public String getGrid() {
        return grid;
    }

    public int getValue() {
        return value;
    }

    private final String grid;
    private final int value;
}
