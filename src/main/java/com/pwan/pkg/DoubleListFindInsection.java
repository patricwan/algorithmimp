package com.pwan.pkg;

public class DoubleListFindInsection {
    //https://leetcode-cn.com/problems/intersection-of-two-linked-lists/solution/intersection-of-two-linked-lists-shuang-zhi-zhen-l/
    public static void main(String[] args) {
        System.out.println("This is the start of main program");
        ListNode list1 = new ListNode(3);
        list1.nextNode = new ListNode(4);
        list1.next().nextNode = new ListNode(7);
        list1.next().next().nextNode = new ListNode(9);
        list1.next().next().next().nextNode = new ListNode(10);


        ListNode list2 = new ListNode(2);
        list2.nextNode = new ListNode(5);
        list2.nextNode.nextNode = new ListNode(7);
        list2.nextNode.nextNode.nextNode = new ListNode(8);
        list2.nextNode.nextNode.nextNode.nextNode = new ListNode(10);

        ListNode insectNode = getIntersectionNode(list1, list2);
        System.out.println(insectNode);
    }


    //
    public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {

        ListNode ha = headA, hb = headB;
        while (ha != hb) {
            ha = (ha != null) ? ha.next() : headB;
            hb = (hb != null) ? hb.next() : headA;
        }
        return ha;

    }


}
