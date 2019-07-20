package com.pwan.pkg;

//https://leetcode-cn.com/problems/redundant-connection/description/?utm_source=LCUS&utm_medium=ip_redirect&utm_campaign=transfer2china
//https://blog.csdn.net/qq_41593380/article/details/81146850
public class FindRedundantConnection {

    public static void main(String[] args) {
        System.out.print("This is the start of the program");
        //[[1,2], [2,3], [3,4], [1,4], [1,5]]
        int[][] all = {{1,2},{2,3}, {3,4}, {1,4}, {1,5}};
        findRedundantConnection(all);


    }

    public static int[] findRedundantConnection(int[][] edges) {
        int[] parent = new int[edges.length + 1];
        for (int i = 0; i < edges.length + 1; i++) {
            parent[i] = i;
            System.out.println(i + " parent " + i);
        }


        int[] res = null;

        for (int[] is : edges) {
            //Got every edge: start x, end y.
            int x = is[0];
            int y = is[1];
            System.out.println("look one edge x " + x + " y " + y);

            while (x != parent[x]) {
                x = parent[x];
            }
            System.out.println("finally x: " +x  + " after this loop ");
            while (y != parent[y]) {
                y = parent[y];
            }
            System.out.println("finally y: " +y  + " after this loop ");

            if (x == y) {
                res = is;
            } else {
                //What means? :  set y as x's parent.
                parent[x] = y;
            }

        }

        return res;
    }
}
