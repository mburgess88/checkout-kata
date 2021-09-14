package dev.michaelburgess.checkout;

import dev.michaelburgess.checkout.exception.DuplicatePricingException;
import dev.michaelburgess.checkout.pricing.PromotionalPrice;
import dev.michaelburgess.checkout.pricing.Pricing;
import dev.michaelburgess.checkout.pricing.StandardPrice;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CheckoutTransactionTest {

    private ArrayList<Pricing> prices;

    /*

    In a normal supermarket, items are identified by ‘stock keeping units’ or ‘SKUs’. In our store, we will use
    individual letters of the alphabet, A, B, C etc, as the SKUs.

    Our goods are priced individually.

    In addition, some items are multipriced: buy n of them and which will cost you y. For example, item A might cost 50
    pence individually but this week we have a special offer where you can buy 3 As for £1.30.

    Item Unit Price Special Price
    A   50      3 for 130
    B   30      2 for 45
    C   20
    D   15

    Our checkout accepts items in any order so if we scan a B, then an A, then another B, we will recognise
    the two B’s and price them at 45 (for a total price so far of 95).

    Extra points: Because the pricing changes frequently we will need to be able to pass in a set of pricing
    rules each time we start handling a checkout transaction.
     */


    @Before
    public void before() {
        prices = new ArrayList<>();
        prices.add(new StandardPrice("C", 20));
        prices.add(new StandardPrice("D", 15));
        prices.add(new PromotionalPrice("A", 50, 3, 20));
        prices.add(new PromotionalPrice("B", 30, 2, 15));
    }

    @Test
    public void noItems(){
        var checkoutTransaction = new CheckoutTransaction(new ArrayList<>());
        Assert.assertEquals(0, checkoutTransaction.calculateTotal(null));
    }

    @Test
    public void addOneItem(){
        var items = new ArrayList<>(List.of("C"));
        var checkoutTransaction = new CheckoutTransaction(prices);
        Assert.assertEquals(20, checkoutTransaction.calculateTotal(items));
    }

    @Test
    public void addTwoOfSameItem(){
        var items = new ArrayList<>(List.of("C", "C"));
        var checkoutTransaction = new CheckoutTransaction(prices);
        Assert.assertEquals(40, checkoutTransaction.calculateTotal(items));

    }

    @Test
    public void addMultipleItems(){
        var items = new ArrayList<>(List.of("C", "D"));
        var checkoutTransaction = new CheckoutTransaction(prices);
        Assert.assertEquals(35, checkoutTransaction.calculateTotal(items));

    }

    @Test
    public void addPromotionItem_NoDiscountApplied(){
        var items = new ArrayList<>(List.of("A"));
        var checkoutTransaction = new CheckoutTransaction(prices);
        Assert.assertEquals(50, checkoutTransaction.calculateTotal(items));
    }
    @Test
    public void addPromotionItems_NoDiscountApplied(){
        var items = new ArrayList<>(List.of("C", "D", "A", "B"));
        var checkoutTransaction = new CheckoutTransaction(prices);
        Assert.assertEquals(115, checkoutTransaction.calculateTotal(items));
    }

    @Test
    public void addPromotionItems_DiscountApplied(){
        var items = new ArrayList<>(List.of("A", "A", "A"));
        var checkoutTransaction = new CheckoutTransaction(prices);
        Assert.assertEquals(130, checkoutTransaction.calculateTotal(items));
    }

    @Test
    public void addPromotionItems_RandomOrder(){
        var items = new ArrayList<>(List.of("A", "B", "A", "C", "A"));
        var checkoutTransaction = new CheckoutTransaction(prices);
        Assert.assertEquals(180, checkoutTransaction.calculateTotal(items));
    }

    @Test(expected = DuplicatePricingException.class)
    public void addOneItem_DuplicatePricing(){
        var prices = new ArrayList<Pricing>();
        prices.add(new StandardPrice("C", 20));
        prices.add(new StandardPrice("C", 30));
        new CheckoutTransaction(prices);
    }

    @Test
    public void addInvalidItem(){
        var items = new ArrayList<>(List.of("X"));
        var checkoutTransaction = new CheckoutTransaction(prices);
        Assert.assertEquals(0, checkoutTransaction.calculateTotal(items));
    }

    @Test
    public void addInvalidItemWithValidItem(){
        var items = new ArrayList<>(List.of("C", "X", "D"));
        var checkoutTransaction = new CheckoutTransaction(prices);
        Assert.assertEquals(35, checkoutTransaction.calculateTotal(items));
    }
}
