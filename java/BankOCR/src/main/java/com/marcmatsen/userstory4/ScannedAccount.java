package com.marcmatsen.userstory4;

import java.util.ArrayList;
import java.util.List;

public sealed class ScannedAccount
    permits InvalidAccount, IllegibleAccount, ValidAccount, AmbiguousAccount {

    private final List<? extends SevenSegDigit> digits;
    
    public ScannedAccount(List<? extends SevenSegDigit> digits) {
        this.digits = digits;
    }

    public List<? extends SevenSegDigit> getDigits() {
        return digits;
    }
    
    static int getAccountNumber(List<ValidDigit> validDigits) {
        int place = 1;
        int accountNumber = 0;
        List<ValidDigit> reversed = validDigits.reversed();
        for (int i = 1; i < 10; i++) {
            accountNumber += reversed.get(i - 1).getDigit().getValue() * place;
            place *= 10;
        }
        return accountNumber;
    }

    /*
    Clarifying the requirement:
    "It turns out that often when a number comes back as ERR or ILL it is because the scanner has failed to pick up on one pipe or underscore for one of the figures. "
    
    The case is where _one_ is missing from _one_ figure
    This can include figure where there's a valid 100% match
    Often many numbers will have a 1-off match
    
    So - the set of candidates (prior to checksum filtering) is:
    - the exact match for all digits
    - When there is exactly one digit with no exact match, then accounts including exact matches for others and all possible for that one
    - When all have exact matches, then for each one with possible non-exact matches, each possible with the others exact
    */
    public static ScannedAccount fromScannedDigitsWithAmbiguity(List<SevenSegDigit> digits) {
        
        int illegibleDigitCount = (int) digits.stream().filter(d -> d instanceof IllegibleDigit).count();
        if (illegibleDigitCount > 1) {
            return new IllegibleAccount(digits);
        }
        
        List<List<ValidDigit>> candidates = new ArrayList<>();
        
        // exact match case
        List<ValidDigit> exactMatchCandidate = (digits.stream().allMatch(d -> d instanceof ValidDigit))
                ? digits.stream().map(d -> (ValidDigit) d).toList()
                : null;
        
        if (exactMatchCandidate != null) {
            candidates.add(exactMatchCandidate);
        }
        
        // only one illegible digit
        if (illegibleDigitCount == 1) {
            int illegibleDigitIndex = -1;
            for (int i = 0; i < digits.size(); i++) {
                if (digits.get(i) instanceof IllegibleDigit) {
                    illegibleDigitIndex = i;
                    break;
                }
            }

            IllegibleDigit illegibleDigit = (IllegibleDigit) digits.get(illegibleDigitIndex);

            // if this illegible digit has no possibles, then there's no way to build a valid account number
            if (illegibleDigit.getPossibleDigits().isEmpty()) {
                return new IllegibleAccount(digits);
            }

            // for each possibledigit, make a new candidate account
            for (Digit possible : illegibleDigit.getPossibleDigits()) {
                List<SevenSegDigit> digitsCopy = new ArrayList<>(digits);
                ValidDigit possibleValidDigit = new ValidDigit(possible.getGrid(), possible, List.of());
                digitsCopy.set(illegibleDigitIndex, possibleValidDigit);
                candidates.add(digitsCopy.stream().map(d -> (ValidDigit) d).toList());
            }
        }
        
        //third case, handle when all valid by making candidate from each possible
        if (illegibleDigitCount == 0) {
            for (int i = 0; i < digits.size(); i++) {
                ValidDigit validDigit = (ValidDigit) digits.get(i);
                for (Digit possible : validDigit.getPossibleDigits()) {
                    List<SevenSegDigit> digitsCopy = new ArrayList<>(digits);
                    ValidDigit possibleValidDigit = new ValidDigit(possible.getGrid(), possible, List.of());
                    digitsCopy.set(i, possibleValidDigit);
                    candidates.add(digitsCopy.stream().map(d -> (ValidDigit) d).toList());
                }
            }    
        }

        List<List<ValidDigit>> validCandidates = candidates.stream().filter(l -> Checksum.isValid(getAccountNumber(l))).toList();
        
        //if there are no valid candidates, it's illegible if there was no 100% valid digit candidate,
        // otherwise InvalidAccount (ERR output) to support that behavior from user story 3
        if (validCandidates.isEmpty()) {
            if (exactMatchCandidate == null) {
                return new IllegibleAccount(digits);
            } else {
                return new InvalidAccount(digits, getAccountNumber(exactMatchCandidate));
            }
        }
        
        // if there's only one valid candidate, assume it's the one
        // including for display purposes - the digits should be those of the found valid account number, not
        // the original digits, illegible or not
        if (validCandidates.size() == 1) {
            return new ValidAccount(validCandidates.getFirst(), getAccountNumber(validCandidates.getFirst()));
        }
        
        // if there are multiple valid account numbers, it's an ambiguous account
        List<Integer> possibleAccountNumbers = validCandidates.stream().map(ScannedAccount::getAccountNumber).toList();
        return new AmbiguousAccount(digits, possibleAccountNumbers);
    }
    
    public static List<List<ValidDigit>> deepCopyListOfLists(List<List<ValidDigit>> l) {
        List<List<ValidDigit>> newlist = new ArrayList<>();
        for (List<ValidDigit> ll : l) {
            List<ValidDigit> newl = new ArrayList<>(ll);
            newlist.add(newl);
        }
        return newlist;
    }
    
    private static void printDigitListList(List<List<ValidDigit>> list) {
        for (List<ValidDigit> l : list) {
            for (ValidDigit d : l) {
                System.out.print(d.getDigit().getValue());
            }
            System.out.println();
        }
    }
}

final class ValidAccount extends ScannedAccount {

    public int getAccountNumber() {
        return accountNumber;
    }

    private final int accountNumber;
    
    ValidAccount(List<ValidDigit> digits, int accountNumber) {
        super(digits);
        this.accountNumber = accountNumber;
    }
}

final class InvalidAccount extends ScannedAccount {

    public int getAccountNumber() {
        return accountNumber;
    }

    private final int accountNumber;
    
    InvalidAccount(List<SevenSegDigit> digits, int accountNumber) {
        super(digits);
        this.accountNumber = accountNumber;
    }
}

final class IllegibleAccount extends ScannedAccount {
    
    IllegibleAccount(List<SevenSegDigit> digits) {
        super(digits);
    }
}

final class AmbiguousAccount extends ScannedAccount {

    public List<Integer> getPossibleAccountNumbers() {
        return possibleAccountNumbers;
    }

    private final List<Integer> possibleAccountNumbers;

    AmbiguousAccount(List<SevenSegDigit> digits, List<Integer> possibleAccountNumbers) {
        super(digits);
        this.possibleAccountNumbers = possibleAccountNumbers;
    }
}
