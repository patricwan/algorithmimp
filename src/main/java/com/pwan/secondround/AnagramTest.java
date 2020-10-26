package com.pwan.secondround;

import java.util.*;

public class AnagramTest {
    public static void main(String[] args) {
        System.out.println("This is the start of AnagramTest");

        String strA = "anpoit";
        String strB = "tanpio";
        System.out.println(" is Anagram " + strA + " and " + strB + " : " + isAnagram(strA, strB));


    }
    public static boolean isAnagram(String strA, String strB) {
        if (strA.length() != strB.length()) {
            return false;
        }

        int[] counter = new int[26];
        for (int i=0; i<strA.length(); i++) {
            counter[strA.charAt(i) - 'a']++;
            counter[strB.charAt(i) - 'a']--;
        }

        for(int i=0; i<26; i++) {
            if(counter[i]!=0) {
                return false;
            }
        }
        return true;
    }

}
