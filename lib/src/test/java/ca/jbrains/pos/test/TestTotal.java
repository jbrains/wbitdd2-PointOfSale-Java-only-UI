package ca.jbrains.pos.test;

import ca.jbrains.pos.PointOfSale;
import ca.jbrains.pos.domain.Basket;
import ca.jbrains.pos.domain.PurchaseProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestTotal {
    @Test
    void noItems() {
        Assertions.assertEquals("Total: CAD 0.00", PointOfSale.handleTotal(new EmptyBasket(), new PurchaseProvider() {
            @Override
            public void startPurchase() {
            }
        }));
    }

    @Test
    void oneItem() {
        Assertions.assertEquals("Total: CAD 1.02", PointOfSale.handleTotal(new NotEmptyBasket(102), new PurchaseProvider() {
            @Override
            public void startPurchase() {
            }
        }));
    }

    private static class EmptyBasket implements Basket {
        @Override
        public void add(int price) {
        }

        @Override
        public int getTotal() {
            return 0;
        }
    }
}
