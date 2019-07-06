package com.pwan.pkg;

public class Anagram {

    public static void main(String[] args) {
        System.out.println("This is the start of main program");

        System.out.println("isAnagram " + isAnagram("angle", "leang"));

    }


    public static boolean isAnagram(String s, String t) {
        int[] sCounts = new int[26];
        int[] tCounts = new int[26];
        //Count to a kind of array with c: how many times occurance?
        for (char ch : s.toCharArray()) {
            sCounts[ch - 'a']++;
        }

        for (char ch : t.toCharArray()) {
            tCounts[ch - 'a']++;
        }

        //Compare these two occurance?
        for (int i = 0; i < 26; i++) {
            if (sCounts[i] != tCounts[i]) {
                return false;
            }
        }
        return true;
    }
}
