package com.pwan.pkg;

public class Pair {

    public int a;

    public int b;

    public Pair(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public static Pair create(int a, int b) {
        return new Pair(a, b);
    }
}
