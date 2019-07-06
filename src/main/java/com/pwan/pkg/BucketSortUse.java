package com.pwan.pkg;

public class BucketSortUse {

    public static void main(String[] args) {
        System.out.println("This is the start of main program");

        int[] testNums = new int[]{7, 3, 9, 11, 15, 19, 5, 4, 17, 15, 23, 14};

        int res = findKthLargest(testNums, 3);

        System.out.println("Found k th largest as " + res);

    }

    public static int findKthLargest(int[] nums, int k) {
        int min = 99999;
        int max = -99999;

        //First find min and max in the array
        for (int i = 0; i < nums.length; i++) {
            if (min > nums[i]) {
                min = nums[i];
            }
            if (max < nums[i]) {
                max = nums[i];
            }
        }

        System.out.println(" max " + max + " min " + min);

        //Build a bucket which is an array, length: max - min + 1;
        // index: 0 => min,  max-minx => max
        int bucket[] = new int[max - min + 1];

        for (int i = 0; i < nums.length; i++) {
            int currentNum = nums[i];
            bucket[currentNum - min]++;
        }

        int count = 0;

        //From biggest to traverse back
        for (int j = bucket.length - 1; j > 0; j--) {
            count += bucket[j];
            if (count >= k) {
                return min + j;
            }

        }

        return -1;
    }

}
