package com.pwan.pkg;

import java.util.Map;
import java.util.HashMap;

//https://leetcode-cn.com/problems/longest-palindrome/description/?utm_source=LCUS&utm_medium=ip_redirect&utm_campaign=transfer2china
public class longestPalindrome {

    public static void main(String[] args) {
        System.out.println("This is the start of main program");

        System.out.println(longestPalindrome("edfkfdoe"));
    }

    public static int longestPalindrome(String str) {
        char[] chas = str.toCharArray();
        //Construct a map with key: char value: occurrance count
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < chas.length; i++) {
            map.put(chas[i], map.getOrDefault(chas[i], 0) + 1);
        }

        int result = 0;
        for (int cnt : map.values()) {
            System.out.println("each Count" + cnt);

            result += cnt / 2 * 2;

            System.out.println("current result " + result);

            if (cnt % 2 == 1 && result % 2 == 0) {
                result++;
            }

        }
         return  result;

    }
}
