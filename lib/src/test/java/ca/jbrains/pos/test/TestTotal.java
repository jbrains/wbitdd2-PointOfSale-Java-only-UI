package ca.jbrains.pos.test;

import ca.jbrains.pos.PointOfSale;
import ca.jbrains.pos.domain.Basket;
import ca.jbrains.pos.domain.PurchaseProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestTotal {
    @Test
    void noItems() {
        EmptyBasket basket = new EmptyBasket();
        Assertions.assertEquals("Total: CAD 0.00", PointOfSale.handleTotal(new PurchaseProvider() {
            @Override
            public void startPurchase() {
            }

            public int getTotal() {
                return basket.getTotal();
            }

            @Override
            public void addItem(int price) {
                basket.add(price);
            }
        }));
    }

    @Test
    void oneItem() {
        Assertions.assertEquals("Total: CAD 1.02", PointOfSale.handleTotal(new StubCurrentPurchaseTotalPurchaseProvider(102)));
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
