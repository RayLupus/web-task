package task;

import org.junit.Assert;
import org.junit.Test;

public class TestingProjTest {

    TestingProj tProj = new TestingProj();
    int[]       A     = { 22, 3, 55, 4, 5, 88 };
    int         N     = A.length;

    @Test
    public void testSortArr() {
        int[] sorted = tProj.sortArr(A, N);
        int[] expecteds = { 3, 4, 5, 22, 55, 88 };
        Assert.assertArrayEquals(expecteds, sorted);
    }

    @Test
    public void testFindMax() {
        Assert.assertEquals(88, tProj.findMax(A, N));
    }

    @Test
    public void testFindAvg() {
        Assert.assertEquals(14, tProj.findAvg(A, N));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSortArrEx1() {
        tProj.sortArr(null, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSortArrEx2() {
        tProj.sortArr(new int[0], 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindMaxEx1() {
        tProj.findMax(null, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindMaxEx2() {
        tProj.findMax(new int[0], 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFinxAvgEx1() {
        tProj.findAvg(null, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFinxAvgEx2() {
        tProj.findMax(new int[0], 0);
    }
}
