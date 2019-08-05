package com.pwan.pkg;

//https://leetcode.com/problems/integer-break/discuss/344729/Java-two-ways%3A-DP-Recursion-with-Memoization
public class IntegerBreak {

    public static void main(String[] args) {
        System.out.println("This is the start of main program");


        //Thoughts: For a number n, if we divide it into i and n - i, then the maximum product = i * max(integerBreak(n - i), n - i).
        //That is either the value of the subproblem f(n - i) or n - i itself.

        System.out.println(integerBreak(34));

    }

    public static int integerBreak(int n) {
        int[] dp = new int[n + 1];
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= i - 1; j++) {
                dp[i] = Math.max(dp[i], j * Math.max(dp[i - j], i - j));
            }
        }
        return dp[n];
    }

}
