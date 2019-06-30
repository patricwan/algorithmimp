package com.pwan.pkg;

public class FindSumFromSortedArray {

    public static void main(String[] args) {

        System.out.println("This is the start of main program");
        int[] testNums = new int[]{1, 3, 7, 11,15, 19};

        int[] foundIndex = findTwoSum(testNums, 16);
        for (int i=0;i < testNums.length; i ++) {
            System.out.println(" found Index " + foundIndex[i]);
        }

    }


    public static int[] findTwoSum(int[] numbers, int target) {
        int i = 0;
        int j = numbers.length - 1;

        boolean bFound = false;
        int tgTwo[] = new int[2];
        while (i!=j && !bFound) {
            int currentSum = numbers[i] + numbers[j];
            if (currentSum == target) {
                tgTwo[0] = i;
                tgTwo[1] = j;
                bFound = true;
            } else if (currentSum < target) {
                i = i + 1;
            } else {
                j = j-1;
            }
        }
        if (!bFound) {
            tgTwo[0] = -1;
            tgTwo[1] = -1;
        }
        return tgTwo;
    }

}
