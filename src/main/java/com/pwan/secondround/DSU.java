package com.pwan.secondround;

public class DSU {

    private int[] allElements;

    private int size;

    public DSU(int size) {
        this.size = size;

        allElements = new int[size];
        //at the beginning, point to itself
        for(int i=0; i<size; i++) {
            allElements[i] = i;
        }
    }

    //Find its root parent of this one
    public int find(int element) {
        int index = element;
        while(allElements[index]!=index) {
            index = allElements[index];
        }

        return index;
    }


    public String toString() {
        String output = "";
        for (int i : allElements) {
            output+=" element " + i + ", parent " + allElements[i];
            output+="\r\n";
        }
        return output;
    }

    public boolean isConnection(int first, int second) {
        return find(first) == find(second);
     }

     public void union(int first, int second) {
        int firstRoot = find(first);
        int secondRoot = find(second);

        if (firstRoot != secondRoot ) {
            for (int i=0; i<size; i++) {
                if (allElements[i] == firstRoot) {
                    allElements[i] = secondRoot;
                }
            }
        }
     }

}
