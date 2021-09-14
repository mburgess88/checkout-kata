package dev.michaelburgess.checkout;

import dev.michaelburgess.checkout.exception.DuplicatePricingException;
import dev.michaelburgess.checkout.pricing.Pricing;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public record CheckoutTransaction(List<Pricing> pricings) {

    public CheckoutTransaction(List<Pricing> pricings) {
        Set<String> duplicates = getDuplicates(pricings);
        if (duplicates.isEmpty())
            this.pricings = List.copyOf(pricings);

        else throw new DuplicatePricingException("Cannot process transaction, duplicate prices found");
    }

    private Set<String> getDuplicates(List<Pricing> pricings) {
        return pricings.stream()
                .collect(Collectors.groupingBy(Pricing::sku, Collectors.counting()))
                .entrySet()
                .stream()
                .filter(m -> m.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    public int calculateTotal(List<String> items) {
        return pricings.stream()
                .mapToInt(pricing -> pricing.getTotal(items))
                .sum();
    }
}
