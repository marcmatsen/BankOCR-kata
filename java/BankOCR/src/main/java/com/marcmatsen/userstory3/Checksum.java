package com.marcmatsen.userstory3;

public class Checksum {
    
    /*
    Checksum algorithm as provided:
    
    account number:  3  4  5  8  8  2  8  6  5
    position names:  d9 d8 d7 d6 d5 d4 d3 d2 d1
    
    (d1 + 2*d2 + 3*d3 +..+ 9*d9) mod 11 = 0
    */
    public static boolean isValid(int num) {
    
        int sum = 0;
        for (int i = 1; i < 10; i++) {
            int digit = (num / (int) Math.pow(10, i - 1)) % 10;
            sum += digit * i;
        }
        
        return sum % 11 == 0;
    }
}
