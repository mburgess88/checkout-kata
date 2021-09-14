package dev.michaelburgess.checkout.pricing;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PromotionalPriceTest {

    @Test
    public void ValidateTotalOneItem(){
        var items = new ArrayList<>(List.of("A"));
        var price = new PromotionalPrice("A", 50, 3, 20);
        Assert.assertEquals(50, price.getTotal(items));
    }

    @Test
    public void ValidateTotalTwoItems(){
        var items = new ArrayList<>(List.of("A", "A"));
        var price = new PromotionalPrice("A", 50, 3, 20);
        Assert.assertEquals(100, price.getTotal(items));
    }

    @Test
    public void ValidateTotalThreeItems(){
        var items = new ArrayList<>(List.of("A", "A", "A"));
        var price = new PromotionalPrice("A", 50, 3, 20);
        Assert.assertEquals(130, price.getTotal(items));
    }

    @Test
    public void ValidateTotalFourItems(){
        var items = new ArrayList<>(List.of("A", "A", "A", "A"));
        var price = new PromotionalPrice("A", 50, 3, 20);
        Assert.assertEquals(180, price.getTotal(items));
    }
}
