package com.pwan.secondround;

public class LengthLISTest {
    public static void main(String[] args) {
        System.out.println("This is the start of LengLISTest");

        int[] input = { 5,7,2,4,5,8,5,9,4,10 };
        int max = getLongestIncreasingArray(input);

        System.out.println("max " + max);

    }

    private static int getLongestIncreasingArray(int[] input) {
        int[] dp = new int[input.length];

        for (int i = 0; i < dp.length; i++) {
            dp[i] = 1;
        }

        for (int i=0; i<dp.length; i++) {
            for (int j=0; j<i; j++) {
                if (input[i] > input[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }

        int max = 1;
        for (int i = 0; i < dp.length; i++) {
            max = Math.max(max, dp[i]);
        }
        return max;


    }
}
