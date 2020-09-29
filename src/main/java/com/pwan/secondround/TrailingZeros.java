package com.pwan.secondround;

public class TrailingZeros {
    public static void main(String[] args) {
        System.out.println("This is the main program");

        int result = trailingZeroes(30);

        System.out.println("This is the result " + result);

    }

    public static int trailingZeroes(int n) {
        int zeroCount = 0;
        // We need to use long because currentMultiple can potentially become
        // larger than an int.
        long currentMultiple = 5;
        while (n >= currentMultiple) {
            zeroCount += (n / currentMultiple);
            currentMultiple *= 5;
        }
        return zeroCount;
    }
}
