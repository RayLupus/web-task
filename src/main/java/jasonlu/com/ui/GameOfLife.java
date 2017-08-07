package jasonlu.com.ui;

import java.util.Random;

/**
 * @author xiaochuan.luxc
 */
public class GameOfLife {

    private static int    x      = 9;           // cell x
    private static int    y      = 9;           // cell y
    private static int    times  = 3;           // live times

    private static char   LIVE   = '#';
    private static char   DEAD   = ' ';
    private static Random random = new Random();

    public static void main(String[] args) {
        System.out.println("cell array is " + x + " | " + y + ", and live " + times + " times");
        char[][] init = randomCellArray();
        System.out.println("Initialized cells is ");
        printArray(init);
        for (int i = 1; i <= times; i++) {
            System.out.println("======== Round " + i + " ========");
            init = live(init);
            printArray(init);
        }
    }

    private static char[][] live(char[][] cellArray) {
        char[][] newArray = new char[x][y];
        for (int i = 0; i < cellArray.length; i++) {
            for (int j = 0; j < cellArray[i].length; j++) {
                int count = 0;
                count += check(cellArray, i - 1, j - 1);
                count += check(cellArray, i - 1, j);
                count += check(cellArray, i - 1, j + 1);
                count += check(cellArray, i, j - 1);
                count += check(cellArray, i, j + 1);
                count += check(cellArray, i + 1, j - 1);
                count += check(cellArray, i + 1, j);
                count += check(cellArray, i + 1, j + 1);
                newArray[i][j] = cellArray[i][j];
                if (cellArray[i][j] == LIVE) {
                    if (count > 3 || count < 2) newArray[i][j] = DEAD;
                } else {
                    if (count == 3) newArray[i][j] = LIVE;
                }
            }
        }
        return newArray;
    }

    private static int check(char[][] cellArray, int i, int j) {
        if (i < 0 || j < 0) return 0;
        if (i >= cellArray.length || j >= cellArray[i].length) return 0;
        return cellArray[i][j] == LIVE ? 1 : 0;
    }

    private static void printArray(char[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j]);
            }
            System.out.println();
        }
    }

    private static char[][] randomCellArray() {
        char[][] cellArray = new char[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                cellArray[i][j] = (random.nextInt(2) == 0 ? DEAD : LIVE);
            }
        }
        return cellArray;
    }
}
