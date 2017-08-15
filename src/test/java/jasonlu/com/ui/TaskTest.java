package jasonlu.com.ui;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author xiaochuan.luxc
 */

public class TaskTest {

    String input = "ABCD EFG";

    @Test
    public void testUniqueInOrder() {
        List<Character> results = Task.uniqueInOrder(input);
        Assert.assertEquals(new Character('G'), results.get(0));
        Assert.assertEquals(new Character('F'), results.get(1));
        Assert.assertEquals(new Character('E'), results.get(2));
        Assert.assertEquals(new Character('D'), results.get(3));
        Assert.assertEquals(new Character('C'), results.get(4));
        Assert.assertEquals(new Character('B'), results.get(5));
        Assert.assertEquals(new Character('A'), results.get(6));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUniqueInOrderEx() {
        Task.uniqueInOrder("LAKSFA2ALSK ");
    }

    @Test
    public void testUniqueInOrderBlank() {
        Assert.assertEquals(Task.uniqueInOrder(" ").size(), 0);
        Assert.assertEquals(Task.uniqueInOrder("").size(), 0);
        Assert.assertEquals(Task.uniqueInOrder(null).size(), 0);
    }
}
