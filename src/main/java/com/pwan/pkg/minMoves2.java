package com.pwan.pkg;

import java.util.Arrays;
//https://leetcode-cn.com/problems/minimum-moves-to-equal-array-elements-ii/solution/zui-shao-yi-dong-ci-shu-shi-shu-zu-yuan-su-xiang-d/
public class minMoves2 {
    public static void main(String[] args) {
        System.out.println("This is the start of minMoves2 program");
        int res = minMoves2(new int[]{4,7,5,3,3,4,6,8,7,8,3,11});

        System.out.println(res);

    }
    //中位数是最优解
    //为了方便，我们先假设一共有2n+1个数，它们从小到大排序之后如下：
    // . . . a m b . . .
    //其中m是中位数。此时，m左边有n个数，m右边也有n个数。
    // 我们假设把m左边所有数变成m需要的代价是x，把m右边所有数变成m的代价是y，此时的总代价就是t = x+y

    public static int minMoves2(int[] nums) {

        if(nums==null||nums.length<2){
            return 0;
        }
        Arrays.sort(nums);


        int i=0,j=nums.length-1,res=0;
        while(i<j){
            res+=nums[j--]-nums[i++];
        }
        return res;

    }
}
