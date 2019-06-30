package com.pwan.pkg;

import java.util.Arrays;

public class GreedyShareBiscuits {

    public static void main(String[] args) {
        System.out.println("This is the start of main program");

        int[] reqs = {2,3,2,1,5,6,7,3};
        int[] avails = {5,3,2,1,4,2,2};

        int satifiedNum = findContentChildren(reqs, avails);
        System.out.println(" satified " + satifiedNum);

    }


    public static int findContentChildren(int[] regs, int[] avails) {
        //First sort for requests and avails
        Arrays.sort(regs);
        Arrays.sort(avails);

        //regs index
        int i = 0;
        //avails index
        int j = 0;

        while (i < regs.length && j < avails.length) {
            if (avails[j] > regs[i]) {
                i++;
            }
            j++;
        }
    return i;
        
    }

}
