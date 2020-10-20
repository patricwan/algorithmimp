package com.pwan.secondround;

import java.util.Arrays;

public class AssignCookiesKids {
    public static void main(String[] args) {
        System.out.println("This is the start of Assign Cookies Kids");

        int[] kids = {12, 13, 4, 8, 4, 5, 3, 6, 7};
        int[] cookies = {3, 6, 7, 2,4};

        int count = getContentsOfChildren(kids, cookies);
        System.out.println(" count " + count);
    }

    public static int getContentsOfChildren(int[] kids, int[] cookies) {
        Arrays.sort(kids);
        Arrays.sort(cookies);

        printArray(kids);
        printArray(cookies);
        int i = 0;
        int j =0;

        int count = 0;
        while( i<kids.length && j<cookies.length) {
            //demands < capacity, assign it.
            if (kids[i] <= cookies[j]) {
                i++;
                j++;
                count++;
            } else {
                //Not able to satisfy. Just move to next capacity
                j++;
            }
        }

        return count;
    }


    public static void printArray(int[] intArr) {
        for(int i=0; i<intArr.length; i++) {
            System.out.print(" " + intArr[i]);
        }
        System.out.println(" ");
    }
}
