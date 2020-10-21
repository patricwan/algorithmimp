package com.pwan.secondround;

public class MySqrt {

    public static void main(String[] args) {
        System.out.println(" This is the start of the program");


        System.out.println(" 19 " + getMySqrt(19));
        System.out.println(" 200 " + getMySqrt(200));
    }

    public static int getMySqrt(int num) {
        int start = 1;
        int end = num/2;

        while(start < end) {
            int mid = (start + end)/2 +1;
            if (mid * mid > num) {
                end = mid - 1;
            } else {
                start = mid;
            }
        }

        return start;
    }
}
