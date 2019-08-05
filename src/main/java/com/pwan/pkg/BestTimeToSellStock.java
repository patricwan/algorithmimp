package com.pwan.pkg;

//https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/description/
public class BestTimeToSellStock {
//Say you have an array for which the ith element is the price of a given stock on day i.
//
//Design an algorithm to find the maximum profit. You may complete as many transactions as you like (ie, buy one and sell one share of the stock multiple times) with the following restrictions:
//
//You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
//After you sell your stock, you cannot buy stock on next day. (ie, cooldown 1 day)

    public static void main(String[] args) {
        System.out.println("This is the start of main program");



    }

//https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/discuss/344464/Java-DP-solution-with-O(n2)-runtime-and-O(n)-space
    private static int maxProfit(int[] prices) {
        if (prices.length == 0)
            return 0;

        int len = prices.length;
        int[] cache = new int[len + 2];
        for (int d = len - 2; d >= 0; d--) {
            int minprice = prices[d];
            int profit = 0;
            for (int x = d; x < len; x++) {
                minprice = Math.min(minprice, prices[x]);
                profit = Math.max(profit, prices[x] - minprice + cache[x + 2]);
            }
            cache[d] = profit;
        }
        return cache[0];
    }
}
