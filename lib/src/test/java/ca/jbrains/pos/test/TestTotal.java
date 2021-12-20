package ca.jbrains.pos.test;

import ca.jbrains.pos.Barcode;
import ca.jbrains.pos.PointOfSale;
import ca.jbrains.pos.domain.Basket;
import io.vavr.control.Option;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestTotal {
    @Test
    void noItems() {
        Assertions.assertEquals("Total: CAD 0.00", PointOfSale.handleTotal(() -> 0));
    }

    @Test
    void oneItem() {
        int priceInCents = 102;
        PointOfSale.handleSellOneItemRequest(ignored -> Option.some(priceInCents), new Barcode("11111"));
        Assertions.assertEquals("Total: CAD 1.02", PointOfSale.handleTotal(() -> priceInCents));
    }
}
