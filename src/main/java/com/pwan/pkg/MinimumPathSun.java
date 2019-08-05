package com.pwan.pkg;

//https://leetcode.com/problems/minimum-path-sum/discuss/346990/Java-DP-Approach-with-EASY-explanation
public class MinimumPathSun {
//We can arrive at the bottom right corner either from top of it, or from left side of it. So, If we know the minimum path to
// reach the one
//row above of the destination and one column left of the destination, then the optimal substructure will be as follows:
//minPathSum[m][n] = grid[m,n] + Math.min(minPathSum[m-1][n],
//minPathSum[m][n-1]);

    public static void main(String[] args) {
        System.out.println("This is the start of main program");

        int[][] myGrid = {
                {1,3,1},
                {1,5,1},
                {4,2,1}
        };

        System.out.println(" min path sum " + minPathSum(myGrid));

    }

    public static int minPathSum(int[][] grid) {

        int m = grid.length;
        if (m == 0) {
            return 0;
        }
        int n = grid[0].length;
        if (n == 0) {
            return 0;
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) {
                    continue;

                } else if (i == 0) {
                    grid[i][j] += grid[i][j-1];

                } else if (j == 0) {
                    grid[i][j] += grid[i-1][j];

                } else {
                    grid[i][j] += Math.min(grid[i-1][j], grid[i][j-1]);

                }
            }
        }
        return grid[m-1][n-1];
    }
}
