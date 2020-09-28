package com.pwan.secondround;


import java.util.ArrayList;
import java.util.List;


public class TopoTest {

    public static void main(String[] args) {
        System.out.println("This is the start of Topo test program");

        int[][] allEdges = new int[][]{
                {1,8},
                {1,3},
                {2,3},
                {2,5},
                {8,9},
                {3,4},
                {5,4},
                {9,7},
                {4,7},
                {4,6},
                {5,6}
        };

        //List<List<Integer>> graphEdges = constructGraph(9, allEdges);

        MyGraph mygraph = new MyGraph();
        mygraph.buildGraph(allEdges);
        System.out.println(mygraph.toString());

        mygraph.BFSFindLoop();

    }

    private static List<List<Integer>>  constructGraph(int totalVs, int[][] allEdges) {

        List<List<Integer>> list =  new ArrayList<List<Integer>>();

        for (int i = 0; i < totalVs; ++i) {
            list.add(new ArrayList<Integer>());
        }

        for (int j=0; j <allEdges.length; j++) {


        }



        return list;
    }

}
