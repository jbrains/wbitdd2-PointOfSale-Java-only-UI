package ca.jbrains.pos.test;

import ca.jbrains.pos.PointOfSale;
import ca.jbrains.pos.domain.Basket;
import ca.jbrains.pos.domain.PurchaseProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Locale;

public class TestTotal {
    @Test
    void noItems() {
        Assertions.assertEquals("Total: CAD 0.00", PointOfSale.handleTotal(new PurchaseProvider() {
            @Override
            public void startPurchase() {
            }

            @Override
            public void addPriceOfScannedItem(int price) {
            }

            @Override
            public int getTotal() {
                return 0;
            }
        }));
    }

    @Test
    void oneItem() {
        Assertions.assertEquals("Total: CAD 1.02", PointOfSale.handleTotal(new PurchaseProvider() {
            @Override
            public void startPurchase() {
            }

            @Override
            public void addPriceOfScannedItem(int price) {
            }

            public int getTotal() {
                return 102;
            }
        }));
    }
}
