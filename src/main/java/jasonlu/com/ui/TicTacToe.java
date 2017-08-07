package jasonlu.com.ui;

import java.util.Random;

/**
 * @author xiaochuan.luxc
 */
public class TicTacToe {

    private static int    XL     = 4;           // board x
    private static int    YL     = 4;           // board y

    private static char   X      = 'x';
    private static char   O      = 'o';
    private static char   E      = 'e';
    private static char   N      = 'n';
    private static char   RESULT = N;
    private static Random random = new Random();

    public static void main(String[] args) {
        char[][] init = randomArray();
        printArray(init);
        loop: for (int i = 0; i < init.length; i++) {
            for (int j = 0; j < init[i].length; j++) {
                if (check(init, i, j, i, j + 1, i, j + 2)) break loop;
                if (check(init, i, j, i, j - 1, i, j - 2)) break loop;
                if (check(init, i, j, i + 1, j - 1, i + 2, j - 2)) break loop;
                if (check(init, i, j, i + 1, j, i + 2, j)) break loop;
                if (check(init, i, j, i + 1, j + 1, i + 2, j + 2)) break loop;
                if (check(init, i, j, i - 1, j - 1, i - 2, j - 2)) break loop;
                if (check(init, i, j, i - 1, j, i - 2, j)) break loop;
                if (check(init, i, j, i - 1, j + 1, i - 2, j + 2)) break loop;
            }
        }
        System.out.println(" = " + RESULT);
    }

    private static boolean check(char[][] cellArray, int x, int y, int x1, int y1, int x2, int y2) {
        if (x < 0 || y < 0 || x1 < 0 || y1 < 0 || x2 < 0 || y2 < 0) return false;
        if (x >= XL || y >= YL || x1 >= XL || y1 >= YL || x2 >= XL || y2 >= YL) return false;
        if (cellArray[x][y] == E || cellArray[x1][y1] == E || cellArray[x2][y2] == E) return false;
        if (cellArray[x][y] == cellArray[x1][y1] && cellArray[x1][y1] == cellArray[x2][y2]) {
            if (RESULT != N && RESULT != cellArray[x][y]) {
                RESULT = N;
                return true;
            }
            RESULT = cellArray[x][y];
        }
        return false;
    }

    private static void printArray(char[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j]);
            }
            System.out.println();
        }
    }

    private static char[][] randomArray() {
        char[][] cellArray = new char[XL][YL];
        for (int i = 0; i < XL; i++) {
            for (int j = 0; j < YL; j++) {
                switch (random.nextInt(3)) {
                    case 0:
                        cellArray[i][j] = X;
                        break;
                    case 1:
                        cellArray[i][j] = O;
                        break;
                    case 2:
                        cellArray[i][j] = E;
                        break;
                }
            }
        }
        return cellArray;
    }
}
