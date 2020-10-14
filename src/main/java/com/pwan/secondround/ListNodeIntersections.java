package com.pwan.secondround;

import java.util.HashMap;

public class ListNodeIntersections {

    public static void main(String[] args) {
        System.out.println("This is the start of test ListNode InterSection");
        ListNode listNodeA = new ListNode(3);
        ListNode listNodeB = new ListNode(4);
        buildListNode(listNodeA, listNodeB);

        ListNode listNodeInter = getIntersectionListNodeBytraverse(listNodeA, listNodeB);
        System.out.println(" inter node " + listNodeInter.val);

        ListNode listNodeInter2 = hashMethodTraverse(listNodeA, listNodeB);
        System.out.println(" inter node2 " + listNodeInter2.val);
    }

    public static void buildListNode(ListNode listNodeA, ListNode listNodeB) {
        listNodeA.next = new ListNode(6);
        listNodeA.next.next = new ListNode(8);
        listNodeA.next.next.next = new ListNode(11);

        listNodeB.next = new ListNode(7);
        listNodeB.next.next = new ListNode(9);

        ListNode common1 = new ListNode(12);
        common1.next = new ListNode(13);
        common1.next.next = new ListNode(87);
        common1.next.next.next = new ListNode(16);

        listNodeA.next.next.next.next = common1;
        listNodeB.next.next.next = common1;
    }

    public static ListNode getIntersectionListNodeBytraverse(ListNode listNodeA, ListNode listNodeB) {
        ListNode firstCommon = null;

        ListNode headA = listNodeA;
        ListNode headB = listNodeB;

        while(headA!=headB) {
            if (headA == null) {
                headA = listNodeB;
            } else {
                headA = headA.next;
            }
            if (headB == null) {
                headB = listNodeA;
            } else {
                headB = headB.next;
            }
        }
        return headA;
    }

    public static ListNode hashMethodTraverse(ListNode listNodeA, ListNode listNodeB) {
        ListNode headA = listNodeA;
        ListNode headB = listNodeB;

        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        while(headA !=null) {
            Integer valInt = new Integer(headA.val);
            map.put(valInt, 1);
            headA = headA.next;
        }
        while(headB !=null) {
            if (map.get(headB.val)!=null) {
                return headB;
            }
            headB = headB.next;
        }
        return null;
    }
}
