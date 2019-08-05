package com.pwan.pkg;

import java.util.*;

//https://leetcode-cn.com/problems/perfect-squares/solution/yan-du-you-xian-sou-suo-java-by-eiletxie/
public class PerfectSquares {

    public static void main(String[] args) {
        System.out.println("This is the start of the program");

        System.out.println("got perfect squars " + numSquares(29));

    }
    //对问题建模：将整个问题变成一个图论问题
    //从n到0，每个数字代表一个节点;
    //如果两个数 x 到 y 相差一个完全平方数，则连接一条边
    //我们就得到了一个无权图；
    //原来的问题就转化为，在这个无权图中找出从 n 到 0 的最短路径，所以需要 BFS 来完成
    public static int numSquares(int n) {
        Queue<NumberNode> queue = new LinkedList<>();
        queue.add(new NumberNode(n, 0));

        // 其实一个真正的图的 BSF 是一定会加上 visited 数组来过滤元素的
        boolean[] visited = new boolean[n + 1];
        while (!queue.isEmpty()) {
            //Get current process node value and step
            int num = queue.peek().val;
            int step = queue.peek().step;
            System.out.println("current num " + num + " step " + step);
            queue.remove();

            for (int i = 1; ; i++) {
                //minus the i*i until hit the 0
                int a = num - i * i;
                if (a < 0) {
                    break;
                }
                System.out.println("current i " + i + " remain a " + a);
                // 若 a 已经计算到 0 了，就不必再往下执行
                if (a == 0) {
                    return step + 1;
                }
                if (!visited[a]) {
                    queue.add(new NumberNode(a, step + 1));
                    visited[a] = true;
                }
            }

        }
        return -1;
    }
}
