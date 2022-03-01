package ca.jbrains.pos.test;

import ca.jbrains.pos.FormatMonetaryAmount;
import ca.jbrains.pos.PointOfSale;
import ca.jbrains.pos.Purchase;
import ca.jbrains.pos.domain.PurchaseAccumulator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Locale;

public class TestTotal {
    @Test
    void noItems() {
        Assertions.assertEquals("Total: CAD 0.00", PointOfSale.handleTotal(new PurchaseAccumulator() {
            @Override
            public Purchase completePurchase() {
                return new Purchase(0, Collections.emptyList());
            }

            @Override
            public void addPriceOfScannedItemToCurrentPurchase(int price) {
            }

            @Override
            public boolean isPurchaseInProgress() {
                return false;
            }

        }, new FormatMonetaryAmount(new Locale("en", "US"))));
    }

    @Test
    void oneItem() {
        Assertions.assertEquals("Total: CAD 1.02", PointOfSale.handleTotal(new PurchaseAccumulator() {
            @Override
            public Purchase completePurchase() {
                return new Purchase(102, Collections.emptyList());
            }

            @Override
            public void addPriceOfScannedItemToCurrentPurchase(int price) {
            }

            @Override
            public boolean isPurchaseInProgress() {
                return false;
            }

        }, new FormatMonetaryAmount(new Locale("en", "US"))));
    }
}
