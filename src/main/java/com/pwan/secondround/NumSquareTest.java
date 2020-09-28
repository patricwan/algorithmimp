package com.pwan.secondround;

public class NumSquareTest {

    public static void main(String[] args) {
        System.out.println("This is the start of Num Square Test");

        calSquareNum(131);


    }


    public static int calSquareNum(int num) {
       // pre-calculate the square numbers.
        int max_square_index = (int) Math.sqrt(num) + 1;
        int square_nums[] = new int[max_square_index];
        for (int i = 1; i < max_square_index; ++i) {
            square_nums[i] = i * i;
        }

        int dp[] = new int[num+1];
        dp[0] = 0;

        for(int i=1; i<=num;i++) {
            StringBuilder str = new StringBuilder();
            str.append("each Num " + i + ":");
            dp[i] = num;
            for(int j=1; j *j <= i ; j++) {
                str.append(" d(" + i + "-" + j + "x" + j + ")=d(" + (i-j*j) + ") ");
                dp[i] = Math.min(dp[i], dp[i - j *j] + 1);
            }
            str.append(" final dp[" + i + "]=" + dp[i]);
            System.out.println(str.toString());
        }

        for (int i : dp) {
            System.out.println("dp[" + i + "]=" + dp[i]);
        }
        return dp[num-1];
    }
}
