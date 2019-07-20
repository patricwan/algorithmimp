package com.pwan.pkg;

public class FinishCourses {
    public static void main(String[] args) {
        System.out.println("This is the start of the program");




    }

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        int[] scheduleCources = new int[numCourses];
        boolean[] visited = new boolean[numCourses];
        //store all start points with count
        for (int i = 0; i < prerequisites.length; i++) {
            scheduleCources[prerequisites[i][0]]++;
        }
        for (; ; ) {
            // 找一个 入度 为0的节点。
            int i = 0;
            for (i = 0; i < numCourses; i++) {
                if (!visited[i] && scheduleCources[i] == 0) {
                    break;
                }
            }
            if (i == numCourses) {
                break;
            }
            //Actually delete this point
            // update the node
            for (int k = 0; k < prerequisites.length; k++) {
                //If end point is just what you want to delete
                if (prerequisites[k][1] == i) {
                    scheduleCources[prerequisites[k][0]]--;
                }
            }
            visited[i] = true;
        }
        //All the entry points should have been removed.
        for (int i = 0; i < scheduleCources.length; i++) {
            if (scheduleCources[i] > 0) {
                return false;
            }
        }
        return true;
    }
}
