# checkout-kata

This is a checkout which calculates the total of the items in your basket based on a set of defined prices.

**Requirements**

This application requires:

-Java 16

-Maven

**Assumptions**

Prices must be unique to a sku

for the purposes of this example prices are whole numbers

The total of the transaction is calculated as a whole, rather than a calculated total as each item is scanned

promotional prices can be stored as a total Multibuy discount rather than a discounted item price

Invalid items scanned are ignored from the basket, and a total of the remaining items is calculated