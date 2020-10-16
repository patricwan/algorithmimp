package com.pwan.secondround;

public class SortColorsOneTwoThree {

    public static void main(String[] args) {
        System.out.println("This is the start of sort colors");

        int[] colors = {1, 0, 2, 1, 0, 2, 1, 0, 2, 1, 0, 1, 2, 0, 1};

        sortThreeColors(colors);
        for (int eachColor : colors)  {
            System.out.print( " " + eachColor);
        }

    }

    public static void sortThreeColors(int[] colors) {
            int p0 = 0;
            int p2 = colors.length - 1;

            for (int i=0;i<=p2; i ++) {
                //if we meet 2, swap with p2, continue swap until we meet 1.
                while (i<p2 && colors[i] ==2) {
                    swapTwoInArray(colors, i, p2);
                    p2--;
                }

                //If we meet 0, swap with p0
                if (colors[i] == 0) {
                    swapTwoInArray(colors, i, p0 );
                    p0++;
                }
            }
    }

    /**
     * Swap two ints in an array
     * @param colors
     * @param posA
     * @param posB
     */
    public static void swapTwoInArray(int[] colors, int posA, int posB) {
        int temp = colors[posB];
        colors[posB] = colors[posA];
        colors[posA] = temp;
    }

}
