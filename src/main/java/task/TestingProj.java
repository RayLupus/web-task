package task;

/**
 * ISCG6413 - Testing and Quality assurance
 * 
 * @author Dr. Bashar Barmada
 */
public class TestingProj {

    /**
     * User selection sort algorithm
     */
    public int[] sortArr(int[] A, int N) {
        if (A == null || N == 0) throw new IllegalArgumentException(); // add parameter check
        int[] sA = A;
        int swap;
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (sA[j] < sA[i]) {
                    swap = sA[j];
                    sA[j] = sA[i];
                    sA[i] = swap; // change j to i
                }
            }
        }
        return sA;
    }

    public int findMax(int[] A, int N) {
        if (A == null || N == 0) throw new IllegalArgumentException(); // add parameter check
        int m = A[0];
        for (int i = 0; i < N; i++) { // change N - 1 to N
            if (m < A[i]) m = A[i];
        }
        return m; // change max to m
    }

    public int findAvg(int[] A, int N) {
        if (A == null || N == 0) throw new IllegalArgumentException(); // add parameter check
        int s = 0;
        int a = 0;
        for (int i = 0; i < N; i++) {
            s = +A[i];
        }
        a = s / N;
        return a;
    }

    public static void main(String[] args) {

        TestingProj tProj = new TestingProj();
        int[] A = { 22, 3, 55, 4, 5, 88 };
        int N = A.length;
    }
}
