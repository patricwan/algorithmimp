package com.pwan.secondround;

public class RemoveDuplicateInArray {

    public static void main(String[] args) {
        System.out.println("This is the start of main program");

        int[] nums = {1,2,2,2,2,3,3,4,5,6,6,6,7,8};

        int[] outputs = removeDuplidateNumbersInArray(nums);

        for(int i=0;i<outputs.length; i++) {
            System.out.print(" " + outputs[i]);
        }
        System.out.println("");

    }
    //Double pointer
    private static int[] removeDuplidateNumbersInArray(int[] nums) {
        if (nums.length < 3) {
            return nums;
        }

        int slow = 0;
        int fast = 0;

        while(fast<nums.length-1) {
            if (nums[fast]!=nums[fast+1]) {
                slow++;
                nums[slow] = nums[fast+1];
            }
                fast++;
        }

        return nums;
    }
}
