package com.pwan.secondround;

public class IsBipartiteDFS {

    public static void main(String[] args) {
        System.out.println("This is the start of the IsBiPartieDFS program");

        int[][] graph = {{1,5},        //0 all vertex
                {0,2},        //1
                {1, 3, 4},    //2
                {2,4},        //3
                {3,5},        //4
                {4,0}         //5
        };

        //Loop all the Vertexs ; all the edges of this vertex;
        //Color them
        int[] colors = new int[graph.length];

        boolean result = traverseIsBipartie(graph, colors);
        System.out.println("This is the result of the program " + result);

    }

    public static boolean traverseIsBipartie(int[][] graph, int[] colors) {
        //Colors: 0, not visited; 1, -1 different colors
        for (int i =0; i< graph.length; i++) {
            //i: vertex
            //If the vertex not colored yet, color it to be 1.
            if (colors[i] ==0) {
                colors[i] = 1;
            }
            //Then color all its neighbours to be the negative
            for (int j=0;j<graph[i].length; j++) {
                //graph[i][j]: all neighbour vertex of i.  If not colored yet.
                if (colors[graph[i][j]] == 0) {
                    colors[graph[i][j]] = -1;
                    //If not match the target color.
                } else if (colors[graph[i][j]] != -1) {
                    return false;
                }
            }

        }
        return true;
    }
}
