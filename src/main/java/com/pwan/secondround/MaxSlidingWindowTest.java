package com.pwan.secondround;

import java.util.Deque;
import java.util.LinkedList;

public class MaxSlidingWindowTest {

    public static void main(String[] args) {
        System.out.println("This is the start of main program");

        int[] nums = {3,6,43,6,6,2,5,8,23,14,6,45,67,23,24,68,3,5};
        int kWinSize = 3;

        int[] result = maxSlidingWindow(nums, kWinSize);

        for (int i =0 ;i <result.length; i++) {
            System.out.println(" res " + result[i]);
        }

    }

    public static int[] maxSlidingWindow(int[] nums, int k)  {
        if (nums.length < k) {
            return null;
        }

        //Double directional queue
        //dq.getLast() dq.getFirst
        //dq.removeLast()
        Deque<Integer> dq = new LinkedList<Integer>();

        int res[] = new int[nums.length - k + 1];

        for (int i=0; i<nums.length; i++) {
           //1. head: remove first from head
            if (!dq.isEmpty() && dq.getFirst() < (i - k +1)) {
                dq.removeFirst();
            }

           //2. tail: remove from last
            while(!dq.isEmpty() && nums[i] > nums[dq.getLast()]) {
                dq.removeLast();
            }

           //3. tail: Add current value to tail
            dq.addLast(Integer.valueOf(i));

            //4: head: return current head value
            if (i >= k-1) {
                res[i-k+1] = nums[dq.getFirst()];
            }
        }

        return res;
    }
}
