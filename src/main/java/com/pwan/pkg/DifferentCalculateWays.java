package com.pwan.pkg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


//https://leetcode-cn.com/problems/different-ways-to-add-parentheses/solution/zi-fu-chuan-chu-li-dong-tai-gui-hua-by-wang-sad/
public class DifferentCalculateWays {

    public static void main(String[] args) {
        System.out.println("This is the start of main program");

        List<Integer> listRes = diffWaysToCompute("3+7-6*12+4");
        printList(listRes);

    }

    public static List<Integer> diffWaysToCompute(String input) {
        //分治算法
        List<Integer> list = new ArrayList<>();
        if (input.length() == 0) return list;
        boolean flag = false;

        for (int i = 0; i < input.length(); ++i) {
            //If it is digital, go to next round to find next char.
            if (Character.isDigit(input.charAt(i))) continue;

            //if meet an operator, flag set, below for flag.
            flag = true;

            //Left part and right part
            List<Integer> lList = diffWaysToCompute(input.substring(0, i));
            List<Integer> rList = diffWaysToCompute(input.substring(i + 1, input.length()));

            for (int m : lList) {
                for (int n : rList) {
                    char op = input.charAt(i);
                    int result = 0;
                    switch (op) {
                        case '+':
                            result = m + n;
                            break;
                        case '-':
                            result = m - n;
                            break;
                        case '*':
                            result = m * n;
                            break;
                    }
                    list.add(result);
                }
            }
        }
        if (flag == false) list.add(Integer.parseInt(input));

        return list;
    }


    public static Integer calculate(Integer a, Integer b, Character operator) {
        if (operator.equals('+'))
            return a + b;
        else if (operator.equals('-'))
            return a - b;
        else
            return a * b;
    }


    public static List<Integer> diffWaysToCompute1(String input) {
        List<Integer> num = new ArrayList<>();
        List<Character> operator = new ArrayList<>();
        // separate num & operator
        int tmpNum = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '+' || input.charAt(i) == '-' || input.charAt(i) == '*') {
                num.add(tmpNum);
                tmpNum = 0;
                operator.add(input.charAt(i));
            } else {
                tmpNum = tmpNum * 10 + (input.charAt(i) - '0');
            }
        }
        printList(num);
        num.add(tmpNum);
        printList(operator);

        // dp
        List<Integer>[][] res = new ArrayList[num.size()][num.size()];
        for (int i = 0; i < num.size(); i++) {
            res[i][i] = new ArrayList<>(Arrays.asList(num.get(i)));
        }
        printDoubleLoopArray(res);
        //printList(res);

        for (int k = 1; k < num.size(); k++)
            for (int i = 0; i < num.size() - k; i++) {
                List<Integer> combine = new ArrayList<>();
                for (int j = 0; j < k; j++) {
                    List<Integer> leftNumList = res[i][i + j];
                    List<Integer> rightNumList = res[i + j + 1][i + k];
                    for (Integer leftNum : leftNumList) {
                        for (Integer rightNum : rightNumList) {
                            combine.add(calculate(leftNum, rightNum, operator.get(i + j)));
                        }
                    }
                    res[i][i + k] = new ArrayList(combine);
                }
            }


        return res[0][num.size() - 1];
    }

    public static void printList(List<?> lList) {
        for (int i = 0; i < lList.size(); i++) {
            System.out.print(lList.get(i) + " ");
        }
        System.out.println("");
    }

    public static void printDoubleLoopArray(List<Integer>[][] array) {

        for (int i = 0; i < array.length; i++) {
            //for (int j = 0; j < array.length; j++)
            System.out.println("i " + i);
            printList(array[i][i]);
        }
    }

}
