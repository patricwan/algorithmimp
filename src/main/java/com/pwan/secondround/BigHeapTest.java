package com.pwan.secondround;



import java.util.PriorityQueue;


public class BigHeapTest {

        public static void main(String[] args) {
            System.out.println("This is the start of the test of main program");

            BigHeap bigHeap = new BigHeap();
            bigHeap.add(8);
            bigHeap.add(12);
            bigHeap.add(3);
            bigHeap.add(5);
            bigHeap.add(7);
            bigHeap.add(13);

            System.out.println(bigHeap.getTop());
            bigHeap.printAll();

            System.out.println("First time remove top");
            bigHeap.removeTop();
            bigHeap.printAll();

            System.out.println("Second time remove top");
            bigHeap.removeTop();
            bigHeap.printAll();

            PriorityQueue<Integer> priorityQueue = new PriorityQueue<Integer>();

            priorityQueue.add(new Integer(12));
            priorityQueue.add(new Integer(7));
            priorityQueue.add(new Integer(14));
            priorityQueue.add(new Integer(2));
            priorityQueue.add(new Integer(16));
            priorityQueue.add(new Integer(15));


            System.out.println(priorityQueue.toString());


        }


}
