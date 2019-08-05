package com.pwan.pkg;

//https://leetcode.com/problems/max-area-of-island/description/Backtracking
public class MaxAreaOfIsland {


    public int[][] grid;
    public boolean[][] seen;

    //Given a non-empty 2D array grid of 0's and 1's, an island is a group of 1's (representing land)
    // connected 4-directionally (horizontal or vertical.) You may assume all four edges of the grid are surrounded by water.
    //Find the maximum area of an island in the given 2D array. (If there is no island, the maximum area is 0.)
    public static void main(String[] args) {
        System.out.println("This is the start of the program");

        MaxAreaOfIsland maxArea = new MaxAreaOfIsland();

        int[][] grid = {{0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0},
                {0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0}};
        int maxCount = maxArea.maxAreaOfIsland(grid);
        System.out.println("max count " + maxCount);

    }

    public int area(int r, int c) {
        if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length ||
                seen[r][c] || grid[r][c] == 0)
            return 0;
        seen[r][c] = true;
        return (1 + area(r + 1, c) + area(r - 1, c)
                + area(r, c - 1) + area(r, c + 1));
    }

    public int maxAreaOfIsland(int[][] grid) {
        this.grid = grid;
        seen = new boolean[grid.length][grid[0].length];
        int ans = 0;
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                ans = Math.max(ans, area(r, c));
            }
        }
        return ans;

    }
}





