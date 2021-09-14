package dev.michaelburgess.checkout.pricing;

import java.util.List;

public record PromotionalPrice(String sku, int price, int promotionQty, int promotionDiscount) implements Pricing {

    @Override
    public int getTotal(List<String> items) {
        var totalStandardPrice = Pricing.super.getTotal(items);
        var discount = calculateDiscount(totalStandardPrice);
        return totalStandardPrice - discount;
    }

    private int calculateDiscount(int totalStandardPrice) {
        return ((totalStandardPrice / price) / promotionQty) * promotionDiscount;
    }
}
