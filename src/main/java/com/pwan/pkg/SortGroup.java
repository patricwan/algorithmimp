package com.pwan.pkg;

public class SortGroup {

    public static void main(String[] args) {
        System.out.println("This is the start of main program");


    }

    //Double pointers to do that.
    //一个指向末尾0元素一个指向首位2元素
    public static void sortColors(int[] nums) {
        int index0 = 0;
        int index2 = nums.length - 1;

        for (int i = 0; i <= index2; i++) {
            //If it 0. then swap, this element with
            if (nums[i] == 0) {
                swap(nums, i, index0++);
            }
            //If it is 2, swap the last one with
            else if (nums[i] == 2) {
                swap(nums, i--, index2--);
            }
        }

    }

    public static void swap(int[] arr, int index1, int index2) {
        int temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }
}
