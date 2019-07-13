package com.pwan.pkg;

//https://leetcode-cn.com/problems/single-number-iii/description/?utm_source=LCUS&utm_medium=ip_redirect&utm_campaign=transfer2china
public class singleNumber {

        public static void main(String[] args) {
            System.out.println("This is the start of singleNumber program");
            int a = 9;
            int  b = 7;
            int xor = 0;
            xor ^= a;
            //9:  1001
            System.out.println( xor);
            xor ^= b;
            //7: 111
            //:  1001 XOR 111 = 1110 =>14
            System.out.println( xor);

            int mask = xor & (-xor);
            System.out.println( mask);

            System.out.println(singleNumber(new int[]{3,6,4,3,6}));


       }




    public static int[] singleNumber(int[] nums) {

        int xor = 0;
        for (int num : nums) {
            xor ^= num;
        }

        int mask = xor & (-xor);

        int[] ans = new int[2];
        for (int num : nums) {
            if ( (num & mask) == 0) {
                ans[0] ^= num;
            } else {
                ans[1] ^= num;
            }
        }

        return ans;
    }



}
