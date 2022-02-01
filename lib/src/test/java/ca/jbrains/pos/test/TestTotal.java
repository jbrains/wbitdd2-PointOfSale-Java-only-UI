package ca.jbrains.pos.test;

import ca.jbrains.pos.PointOfSale;
import ca.jbrains.pos.domain.PurchaseAccumulator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestTotal {
    @Test
    void noItems() {
        Assertions.assertEquals("Total: CAD 0.00", PointOfSale.handleTotal(new PurchaseAccumulator() {
            @Override
            public int completePurchase() {
                throw new UnsupportedOperationException();
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
            public int completePurchase() {
                throw new UnsupportedOperationException();
            }

            @Override
            public void addPriceOfScannedItemToCurrentPurchase(int price) {
            }

            public int getTotalOfCurrentPurchase() {
                return 102;
            }
        }));
    }
}
