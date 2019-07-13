package com.pwan.pkg;
//https://leetcode-cn.com/problems/majority-element/solution/javajie-fa-li-yong-mo-er-tou-piao-fa-by-jia-zhong-/
public class majorityElement {

    public static void main(String[] args) {
        System.out.println("This is the start of singleNumber program");
        System.out.println(majorityElement(new int[]{1,3,3,2,6,73,3,2,3,3,8,9,5,3,3}));
    }

    //摩尔投票
    public static int majorityElement(int[] nums) {

        int count=0;//计算当前的数字出现的次数
        int mj=0;//当前判断的元素
        for (int i = 0; i < nums.length; i++)
        {
            if(count==0){//当次数为0时，则换下一判断元素
                mj=nums[i];
                count=1;
            }
            else if (nums[i]==mj) {
                count++;//当前元素等于判断元素，次数加一
            }
            else {
                count--;//不等于则次数减一
            }
        }
        return mj;

    }
}
