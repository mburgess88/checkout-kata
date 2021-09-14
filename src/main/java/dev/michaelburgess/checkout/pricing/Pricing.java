package dev.michaelburgess.checkout.pricing;

import java.util.List;

public interface Pricing {

    String sku();
    int price();

    default int getTotal(List<String> items) {
        var numOfItems = items.stream()
                .filter(item -> item.equalsIgnoreCase(sku()))
                .count();
        return (int) (price() * numOfItems);
    }

}
