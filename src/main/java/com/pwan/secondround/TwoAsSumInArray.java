package com.pwan.secondround;

import java.util.*;

public class TwoAsSumInArray {
    public static void main(String[] args) {

        int[] intArr = {3, 7, 8, 9, 12, 45,2, 3, 5, 6 , 17, 18, 12, 14, 87};

        int[] ret = findTwoNumInArrayAsSumHash(intArr, 23);
        for (int i =0; i<2; i++) {
            System.out.println("ret " + ret[i]);
        }
    }

    public static int[] findTwoNumInArrayAsSumHash(int[] intArr, int sum) {
        HashMap map = new HashMap();

        for (int i=0; i<intArr.length; i++) {
            int remain = sum - intArr[i];
            if (map.containsKey(remain)) {
                return new int[]  {intArr[i], remain};
            }
            map.put(intArr[i], 1);
        }
        return null;

    }

    public static void findTwoNumInArrayAsSum(int[] intArr, int sum) {


    }
}
