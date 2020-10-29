package com.pwan.secondround;

public class LongestPalindromeTest {

    public static void main(String[] args) {
        System.out.println(" This is the start of LongestPalindromeTest");

        int longestLetters = longestPalindrome("padafaxqewrpaaaadsfafsasfp");
        System.out.println("longestLetters " + longestLetters);
    }

    public static int longestPalindrome(String str) {
        int[] charArray = new int[26];

        for(int i=0; i<str.length(); i++) {
            charArray[str.charAt(i) - 'a']++;
        }

        int sum = 0;
        boolean flag = false;
        for(int j=0; j<charArray.length; j++) {
            if (charArray[j]%2 ==0) {
                sum = sum + charArray[j];
            } else {
                sum = sum + charArray[j] - 1;
                if (!flag) {
                    flag = true;
                }
            }
        }

        if (flag) {
            sum++;
        } else {
            sum--;
        }

        return sum;
    }

}
