package com.marcmatsen.userstory3;

import java.util.Arrays;
import java.util.Optional;

public sealed class SevenSegDigit
    permits ValidDigit, IllegibleDigit {
    public String scannedGrid;
    SevenSegDigit(String scannedGrid) {
        this.scannedGrid = scannedGrid;
    }

    static SevenSegDigit parse(String scannedGrid) {
        Optional<Digit> digit = Arrays.stream(Digit.values())
                .filter(d -> d.getGrid().equals(scannedGrid))
                .findFirst();
        if (digit.isPresent()) {
            return new ValidDigit(scannedGrid, digit.get());
        } else {
            return new IllegibleDigit(scannedGrid);
        }
    }
}

final class IllegibleDigit extends SevenSegDigit {
    public IllegibleDigit(String scannedGrid) {
        super(scannedGrid);
    }
}

final class ValidDigit extends SevenSegDigit {

    public Digit getDigit() {
        return digit;
    }

    private Digit digit;

    public ValidDigit(String scannedGrid, Digit digit) {
        super(scannedGrid);
        this.digit = digit;
    }
}


