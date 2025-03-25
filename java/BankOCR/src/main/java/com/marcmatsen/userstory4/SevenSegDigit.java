package com.marcmatsen.userstory4;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public sealed class SevenSegDigit
    permits ValidDigit, IllegibleDigit {
    private final String scannedGrid;

    public String getScannedGrid() {
        return scannedGrid;
    }

    public List<Digit> getPossibleDigits() {
        return possibleDigits;
    }

    private final List<Digit> possibleDigits;
    
    SevenSegDigit(String scannedGrid, List<Digit> possibleDigits) {
        this.scannedGrid = scannedGrid;
        this.possibleDigits = possibleDigits;
    }

//    static SevenSegDigit parse(String scannedGrid) {
//        Optional<Digit> digit = Arrays.stream(Digit.values())
//                .filter(d -> d.getGrid().equals(scannedGrid))
//                .findFirst();
//        if (digit.isPresent()) {
//            return new ValidDigit(scannedGrid, digit.get());
//        } else {
//            return new IllegibleDigit(scannedGrid, getPossibleDigits(scannedGrid));
//        }
//    }

    static SevenSegDigit parse(String scannedGrid) {
        Optional<Digit> exactDigit = getExactDigit(scannedGrid);
        List<Digit> possibleDigits = getOneOffDigits(scannedGrid);
        if (exactDigit.isPresent()) {
            return new ValidDigit(scannedGrid, exactDigit.get(), possibleDigits);
        } else {
            return new IllegibleDigit(scannedGrid, possibleDigits);
        }
    }
    
    private static Optional<Digit> getExactDigit(String scannedGrid) {
        return Arrays.stream(Digit.values())
                .filter(d -> d.getGrid().equals(scannedGrid))
                .findFirst();
    }
    
    private static List<Digit> getOneOffDigits(String scannedGrid) {
        return Arrays.stream(Digit.values())
                .filter(d -> stringXor(d.getGrid(), scannedGrid) == 1)
                //.map(d -> new PossibleDigit(scannedGrid, d))
                .toList();
    }
    
    private static int stringXor(String a, String b) {
        int result = 0;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) {
                result++;
            }
        }
        return result;
    }
}

final class IllegibleDigit extends SevenSegDigit {
    
    public IllegibleDigit(String scannedGrid, List<Digit> possibleDigits) {
        super(scannedGrid, possibleDigits);
    }
}

final class ValidDigit extends SevenSegDigit {

    public Digit getDigit() {
        return digit;
    }

    private final Digit digit;

    public ValidDigit(String scannedGrid, Digit digit, List<Digit> possibleDigits) {
        super(scannedGrid, possibleDigits);
        this.digit = digit;
    }
}


