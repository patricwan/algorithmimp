package com.pwan.secondround;

public class BigHeap {

    int[] elements;

    int size = 0;

    private static int maxLength = 1000;

    public int getSize() {
        return size;
    }

    public BigHeap() {
        elements = new int[maxLength];
    }

    public boolean add(int newElement) {

        int i = size;
        if (size ==0 ) {
            size++;
            elements[0] = newElement;
        } else {
            size++;
            siftUp(i, newElement);
        }

        return true;
    }

    public void siftUp(int maxSize, int element) {
        int i = maxSize;

        elements[i] = element;

        while(i>0) {
            int parentId = (i -1) /2;

            if (elements[parentId] < element)  {
                swap(elements, parentId, i);
            }
            i = parentId;
        }

    }

    public int getTop() {
        if (size > 0) {
            return elements[0];
        } else {
            return -1;
        }
    }

    public int removeTop() {
        int retValue = -1;
        if (size > 0) {
            retValue = elements[0];
            siftDown();
        } else {
            System.err.println("Nothing to delete");
            return -1;
        }

        return retValue;
    }



    public void siftDown() {
        elements[0] = elements[size-1];
        size--;

        int index = 0;

        while(index < size / 2) {
            int left = 2 * index + 1;
            int right =  2 * index + 2;

            int biggerChild = findBigger(elements, left, right);

            if (elements[index] < elements[biggerChild]) {
                swap(elements, index, biggerChild);
                index = biggerChild;
            }  else  {
                break;
            }
        }
    }

    public void swap(int[] array, int indexA, int indexB) {
        if (array.length < indexA || array.length < indexB) {
            System.err.println("Errors");
        }

        int temp = array[indexA];
        array[indexA] = array[indexB];
        array[indexB] = temp;
    }

    public void printAll() {
        System.out.println("Print All");
        for (int i =0; i<size; i ++) {
            System.out.println("pos" + i + " value " + elements[i]);
        }
    }

    public int findBigger(int[] array, int indexA, int indexB) {
        if (array.length < indexA || array.length < indexB) {
            System.err.println("Errors");
            return 0;
        }

        if (array[indexA] < array[indexB]) {
            return indexB;
        } else {
            return indexA;
        }
    }
}
