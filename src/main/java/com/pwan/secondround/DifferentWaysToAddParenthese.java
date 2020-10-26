package com.pwan.secondround;

import java.util.*;


public class DifferentWaysToAddParenthese {
    public static void main(String[] args) {
        System.out.println("This is the start of program DifferentWaysToAddParenthese");
        List<Integer> differentWays = calculateDifferentWaysToCompute("2+4*5-6+7-4+7-3*5-3+2");
        for (Integer eachRes: differentWays) {
            System.out.print(" " + eachRes);
        }
        System.out.println();
    }

    public static List<Integer> calculateDifferentWaysToCompute(String chars) {
        List<Integer> res = new ArrayList<Integer>();

        //考虑是全数字的情况
        int num = 0;
        int index = 0;
        /* while (index < chars.length() && !isOperationChar(chars.charAt(index))) {
            num = num * 10 + chars.charAt(index) - '0';
            index++;
        }
        //将全数字的情况直接返回
        if (index == chars.length()) {
            res.add(num);
            return res;
        }*/
        if (chars.length() == 1 && !isOperationChar(chars.charAt(0))) {
            int value = Integer.valueOf(chars);
            System.out.println("got the only char num " + Integer.valueOf(chars));
            res.add(new Integer(value));
            return res;
        }

        for (int i=0; i<chars.length(); i++) {
            //if it is an operator, + - *, we can divide and recursive
            char operator = chars.charAt(i);
            if (isOperationChar(operator)) {
                System.out.println("Find an operator " + operator);
                List<Integer> resLeft = calculateDifferentWaysToCompute(chars.substring(0, i));
                List<Integer> resRight = calculateDifferentWaysToCompute(chars.substring(i+1));

                //combine the results
                for (int j =0; j<resLeft.size(); j++) {
                    for (int k=0; k<resRight.size(); k++) {
                        int calRes = calculate(resLeft.get(j), resRight.get(k), operator);
                        System.out.println("add res " + calRes);
                        res.add(calRes);
                    }
                }
            }
        }

        return res;
    }

    private static int calculate(int a, int b, char operator) {
        int res = 0;
        if (operator == '+') {
            res = a + b;
        } else if (operator == '-') {
            res = a-b;
        } else if (operator == '*') {
            res = a * b;
        }
        return res;
    }

    private static boolean isOperationChar(char ch) {
        return ch == '+' || ch == '-' || ch == '*';
    }

}
