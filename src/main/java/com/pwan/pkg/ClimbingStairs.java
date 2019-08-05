package com.pwan.pkg;

//https://leetcode.com/problems/climbing-stairs/description/
public class ClimbingStairs {

    public static void main(String[] args) {
        System.out.println("This is the start of the main program");

        System.out.println(" steps " + climbStairs(19));
    }

    //Dynamic Programming
    //As we can see this problem can be broken into subproblems,
    //and it contains the optimal substructure property i.e. its optimal solution can be constructed efficiently
    // from optimal solutions of its subproblems, we can use dynamic programming to solve this problem.
    //One can reach ith step in one of the two ways:
    //Taking a single step from (i−1}th step.
    //Taking a step of 2 from (i−2)th step.  So, the total number of ways to reach i^{th} th is equal to
    // sum of ways of reaching (i−1)th
    //step and ways of reaching (i−2) th step.

    //Let dp[i] dp[i] denotes the number of ways to reach on i^{th}ith step:
    //dp[i]=dp[i-1]+dp[i-2]
    public static int climbStairs(int n) {
        if (n == 1) {
            return 1;
        }
        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }
}
