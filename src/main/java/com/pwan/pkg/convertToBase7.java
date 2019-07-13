package com.pwan.pkg;

//https://leetcode-cn.com/problems/base-7/comments/
public class convertToBase7 {

    public static void main(String[] args) {
        System.out.println("This is the start of convertToBase7 program");

        System.out.println(convertToBase7(456));

    }

    public static String convertToBase7(int num) {

        if(num==0) return "0";
        int flag=0; String str="";
        if(num<0) {flag=1;num=-num;}
        while(num!=0){
            str=String.valueOf(num%7)+str;
            //
            num=num/7;
        }
        if(flag==1) str="-"+str;
        return str;
    }
}
