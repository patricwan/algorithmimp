package com.pwan.pkg;

//https://leetcode-cn.com/problems/kth-smallest-element-in-a-sorted-matrix/description/?utm_source=LCUS&utm_medium=ip_redirect&utm_campaign=transfer2china
public class kthSmallest {

    public static void main(String[] args) {

        System.out.println("This is the start of main program");
        //3 *3
        int[][] mat = new int[3][3];
        mat[0][0] = 1;
        mat[0][1] = 3;
        mat[0][2] = 4;

        mat[1][0] = 2;
        mat[1][1] = 5;
        mat[1][2] = 8;

        mat[2][0] = 7;
        mat[2][1] = 11;
        mat[2][2] = 12;

        System.out.println(kthSmallest(mat, 5));
    }

    /**
     * 每次的起始位置都是矩阵左下角的位置，如果当前位置即matrix[i][j] <= mid
     * 那么说明当前位置上面的元素都小于当前元素，也就是有 i + 1 个元素小于等于当前元素
     * 也就是总共有 i + 1 个元素小于等于 mid。比如题目的例子
     *
     * [ [1，5，9]，[10，11，13]，[12，13，15] ]
     *
     * 要找排位第八的元素，首先 lo = 1，hi = 15，那么 mid = 8。
     * 明显matrix[i][j] = 12 > mid，往上一行找，matrix[i][j] = 10 > mid，
     * 再往上一行找，matrix[i][j] = 1 <= mid，
     * cnt += 1，往右找，matrix[i][j] = 5 <= mid，cnt += 1，
     * 继续往右，matrix[i][j] > mid，停止。这时 cnt = 2，说明这个矩阵总共有2个元素小于等于8，
     * 而目标是要找到第八个，所以需要往后半段去找，
     * 也就是 lo = mid + 1 重复以上步骤去后半段找到目标排名的元素。
     * @param matrix
     * @param k
     * @return
     */
    public static int kthSmallest(int[][] matrix, int k) {

        int m = matrix.length, n = matrix[0].length;
        //define low as first element in the matrix, high as last element in the matrix
        int lo = matrix[0][0], hi = matrix[m-1][n-1];
        while (lo <= hi) {
            //find mid value.
            int cnt = 0, mid = lo + (hi - lo) / 2;

            //i: from last  to first, j: from first to last
            //iterate all elements
            int i = m - 1, j = 0;
            while (i >= 0 && j < n) {
                if (matrix[i][j] <= mid) {
                    cnt += i + 1;
                    j++;
                } else {
                    i--;
                }
            }
            if (cnt < k) lo = mid + 1;
            else hi = mid - 1;
        }
        return lo;

    }
}
