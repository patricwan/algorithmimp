package com.pwan.secondround;

public class IsBiPartieTest {
    public static void main(String[] args) {
        System.out.println("This is the start of  IsBiPartieTest");

        int[][] graph = {{1,5},        //0 all vertex
                         {0,2},        //1
                         {1, 3, 4},    //2
                         {2,4},        //3
                         {3,5},        //4
                         {4,0}         //5
                 };

        boolean isBiParti = isBiPartieGraph(graph);

        System.out.println(" is BiPartie:" + isBiParti);

    }

    public static boolean isBiPartieGraph(int[][] graph) {
        UnionFind unionFind = new UnionFind(graph.length);
        // 遍历每个顶点，将当前顶点的所有邻接点进行合并
        for (int i =0; i< graph.length; i++) {
            for (int j=0; j <graph[i].length; j++) {
                if (unionFind.isSameAncestor(i, graph[i][j])) {     //If current node, has the same ancestor with any vertex
                    return false;
                }
                //Merge all its neighbour vertex=
                unionFind.merge(graph[i][0], graph[i][j]);
            }
        }
        return true;
    }
}