package com.pwan.secondround;

public class UnionFind {
    //array to represent UnionFind
    //i:         0, 1, 2, 3, 4 , 5, 6, 7, 8
    //parent[i]: 1, 1, 2, 3, 4,  5, 6, 7, 8
    int parent[];

    public UnionFind(int size) {
        parent = new int[size];
        //initialize all the parents to be itself.
        for (int i=0; i <size; i++) {
            parent[i] = i;
        }
    }

    public int find(int element) {
        if (parent[element] == element) {
            return element;
        }  else {
            return find(parent[element]);
        }
    }

    public boolean isSameAncestor(int i, int j) {
        return find(i) == find(j);
    }

    //Merge, take the second one as parent.
    public void merge(int i, int j) {
        parent[i] = j;
    }

}
