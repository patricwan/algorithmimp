package com.pwan.pkg;


public class SqrRoot {
    public static void main(String[] args) {
        System.out.println("This is the start of main program");

        System.out.println(" 16 sqrt is " + newtonWay(16));
    }

    //https://blog.csdn.net/ccnt_2012/article/details/81837154
    //Xn+1 = Xn - (Xn * Xn - num) / (2 * Xn)
    public static float newtonWay(int num) {
        if (num == 0) {
            return 0;
        }
        float current = num;
        float last = 0;
        while (Math.abs(current - last) >= 1) {
            last = current;
            current = current - (current * current - num) / (2 * num);
        }
        return current;

    }


}

