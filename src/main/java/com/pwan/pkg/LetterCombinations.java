package com.pwan.pkg;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//https://leetcode.com/problems/letter-combinations-of-a-phone-number/description/
public class LetterCombinations {

    Map<String, String> phone = new HashMap<String, String>() {{
        put("2", "abc");
        put("3", "def");
        put("4", "ghi");
        put("5", "jkl");
        put("6", "mno");
        put("7", "pqrs");
        put("8", "tuv");
        put("9", "wxyz");
    }};

    List<String> output = new ArrayList<String>();

    //Given a string containing digits from 2-9 inclusive, return all possible letter combinations that the number could represent.
    //A mapping of digit to letters (just like on the telephone buttons) is given below. Note that 1 does not map to any letters.
    public static void main(String[] args) {
        System.out.println("This is the start of main program");

        LetterCombinations letterComb = new LetterCombinations();

        List<String> allCombins = letterComb.letterCombinations("357");

        for (String eachStr : allCombins) {

            System.out.println("comb " + eachStr);
        }
    }


    public void backtrack(String combination, String next_digits) {
        // if there is no more digits to check
        if (next_digits.length() == 0) {
            // the combination is done
            output.add(combination);
        }
        // if there are still digits to check
        else {
            // iterate over all letters which map
            // the next available digit
            String digit = next_digits.substring(0, 1);
            String letters = phone.get(digit);
            for (int i = 0; i < letters.length(); i++) {
                String letter = phone.get(digit).substring(i, i + 1);
                // append the current letter to the combination
                // and proceed to the next digits
                backtrack(combination + letter, next_digits.substring(1));
            }
        }
    }

    public List<String> letterCombinations(String digits) {
        if (digits.length() != 0)
            backtrack("", digits);
        return output;
    }

}
