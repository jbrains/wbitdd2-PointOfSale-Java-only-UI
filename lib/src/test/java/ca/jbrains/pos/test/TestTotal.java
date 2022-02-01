package ca.jbrains.pos.test;

import ca.jbrains.pos.PointOfSale;
import ca.jbrains.pos.Purchase;
import ca.jbrains.pos.domain.PurchaseAccumulator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestTotal {
    @Test
    void noItems() {
        Assertions.assertEquals("Total: CAD 0.00", PointOfSale.handleTotal(new PurchaseAccumulator() {
            @Override
            public Purchase completePurchase() {
                return new Purchase(0);
            }

            @Override
            public void addPriceOfScannedItemToCurrentPurchase(int price) {
            }

            @Override
            public int getTotalOfCurrentPurchase() {
                return 0;
            }
        }));
    }

    @Test
    void oneItem() {
        Assertions.assertEquals("Total: CAD 1.02", PointOfSale.handleTotal(new PurchaseAccumulator() {
            @Override
            public Purchase completePurchase() {
                return new Purchase(102);
            }

            @Override
            public void addPriceOfScannedItemToCurrentPurchase(int price) {
            }

            @Override
            public int getTotalOfCurrentPurchase() {
                return 0;
            }
        }));
    }
}
