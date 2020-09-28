package com.pwan.secondround;

import java.util.Stack;

public class MaxAreaOfIsland {

    public static void main(String[] args) {
        System.out.println("This is the start of the MaxAreaOfIsland Test");

        int[][] grid = {
                {0,0,1,0,0,0,0,1,0,0,0,0,0},
                {0,0,0,0,0,0,0,1,1,1,0,0,0},
                {0,1,1,0,1,0,0,0,0,0,0,0,0},
                {0,1,0,0,1,1,0,0,1,0,1,0,0},
                {0,1,0,0,1,1,0,0,1,1,1,0,0},
                {0,0,0,0,0,0,0,0,0,0,1,0,0},
                {0,0,0,0,0,0,0,1,1,1,0,0,0},
                {0,0,0,0,0,0,0,1,1,0,0,0,0}
                       };

        int maxArea = findMaxAreaOfIsland(grid);

        System.out.println("the max area is  " + maxArea);

    }

    public static int findMaxAreaOfIsland(int[][] grid) {
        int maxArea = 0;

        Stack<int[]> stack = new Stack<int[]>();

        int[][] pointerArray = {
                {-1, 0},    //left 1
                {1, 0},     //right 1
                {0, 1},     //below 1
                {0, -1}     //above 1
               };

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(" i:" + i + " j:" + j);

                int[] iteratePos = {i, j};
                if (grid[i][j] ==1) {
                    stack.push(iteratePos);
                }

                int currMaxArea = 0;   //Current max area from current pos perspective
                //Process the stack
                while(!stack.isEmpty()) {

                    //if
                    int[] workingPos = stack.pop();

                    if (workingPos[0] < 0 || workingPos[1] < 0 || workingPos[0] >= grid.length
                         || workingPos[1] >= grid[i].length || grid[workingPos[0]][workingPos[1]] ==0 ) {
                        continue;
                    }
                    currMaxArea++;
                    //Then traverse through all its neighbours and process it.
                    //Push 4 neighbours to stack
                    for (int[] eachPosBias: pointerArray) {
                        int[] neighbourPos = {workingPos[0] +eachPosBias[0] , workingPos[1] +eachPosBias[1]};
                        stack.push(neighbourPos);
                    }
                    grid[workingPos[0]][workingPos[1]] = 0;
                }
                maxArea = Math.max(currMaxArea, maxArea);
            }
            System.out.println("");
        }

        return maxArea;
    }

}
