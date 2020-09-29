package com.pwan.secondround;

public class MinEditDistance {

    public static void main(String[] args) {
        System.out.println("This is the start of the main program");

        int distance = minEditDistance("apple" , "jacket");

        System.out.println("This is the end of the program distance:" + distance);

    }

    //   0, 1, ..... i-1
    //   0, 1, ......j-1
    //considering the last step
    public static int minEditDistance(String strA, String strB) {
        int[][] dp = new int[strA.length()+1][strB.length()+1];

        dp[0][0] = 0;

        for (int i = 0; i < strA.length() + 1; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j < strB.length() + 1; j++) {
            dp[0][j] = j;
        }

        for(int i=1; i<=strA.length(); i++) {
            for (int j=1; j<=strB.length(); j++) {
                if (strA.charAt(i-1) == strB.charAt(j-1)) {
                    dp[i][j] = dp[i-1][j-1];
                } else {
                    dp[i][j] = Math.min(dp[i-1][j] + 1, dp[i][j-1] + 1);
                }
            }
        }

        return dp[strA.length()-1][strB.length()-1];
    }
}
