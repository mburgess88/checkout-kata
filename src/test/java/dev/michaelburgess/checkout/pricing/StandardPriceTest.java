package dev.michaelburgess.checkout.pricing;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class StandardPriceTest {

    @Test
    public void ValidateTotalOneItem(){
        var items = new ArrayList<>(List.of("A"));
        var price = new StandardPrice("A", 20);
        Assert.assertEquals(20, price.getTotal(items));
    }

    @Test
    public void ValidateTotalTwoItems(){
        var items = new ArrayList<>(List.of("A", "A"));
        var price = new StandardPrice("A", 20);
        Assert.assertEquals(40, price.getTotal(items));
    }

    @Test
    public void ValidateTotalTwoDifferentItems(){
        var items = new ArrayList<>(List.of("A", "B"));
        var price = new StandardPrice("A", 20);
        Assert.assertEquals(20, price.getTotal(items));
    }


}
