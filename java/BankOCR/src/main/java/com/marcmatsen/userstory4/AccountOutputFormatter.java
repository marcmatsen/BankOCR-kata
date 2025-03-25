package com.marcmatsen.userstory4;

import java.util.List;

public class AccountOutputFormatter {
    
    public String formatAccountForFileOutput(ScannedAccount account) {
        String formattedDigits = String.join("", account.getDigits().stream()
                .map(this::formatDigit)
                .toList());
        return switch (account) {
            case ValidAccount va -> formattedDigits;
            case IllegibleAccount ia -> formattedDigits + " ILL";
            case InvalidAccount ia -> formattedDigits + " ERR";
            case AmbiguousAccount aa -> {
                List<String> possibleAccountNumbers = aa.getPossibleAccountNumbers()
                        .stream()
                        .sorted()
                        .map(n -> String.format("%09d", n))
                        .toList();
                yield formattedDigits + " AMB ['" + String.join("', '", possibleAccountNumbers) + "']";
            }
            default -> throw new IllegalStateException("Unexpected value: " + account);
        };
    }
    
    private String formatDigit(SevenSegDigit d) {
        return switch (d) {
            case ValidDigit vd -> String.valueOf(vd.getDigit().getValue());
            case IllegibleDigit id -> "?";
            default -> throw new IllegalStateException("Unexpected value: " + d);
        };
    }
}
