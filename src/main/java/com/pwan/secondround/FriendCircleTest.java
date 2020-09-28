package com.pwan.secondround;

import java.util.*;


public class FriendCircleTest {
    public static void main(String[] args) {
        System.out.println("This is the start of the main program");

        int[][] friendsMatrix = {
                {1, 1 , 0, 0, 0 ,0 },
                {1, 1,  0, 0, 0, 0 },
                {0, 0,  1, 1, 1, 0 },
                {0, 0, 1, 1, 0, 0 },
                {0, 0, 1, 0, 1, 0 },
                {0, 0, 0, 0, 0, 1 } };

        List<List<Integer>> list = transformToEdges(friendsMatrix);


    }

    private static List<List<Integer>> transformToEdges(int[][] matrix) {
        List<List<Integer>> list  = new ArrayList<List<Integer>>();

        for(int i = 0; i<matrix.length; i++) {
            for (int j=0;j<i; j++) {
                if (i!=j && matrix[i][j] ==1) {
                    List<Integer> pointList = new ArrayList<Integer>();
                    pointList.add(new Integer(i));
                    pointList.add(new Integer(j));
                    list.add(pointList);
                }
            }
        }
        return list;
    }

}
