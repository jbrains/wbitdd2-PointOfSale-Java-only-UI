package ca.jbrains.pos.test;

import ca.jbrains.pos.PointOfSale;
import ca.jbrains.pos.domain.PurchaseProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestSellMultipleItems {
    private boolean startPurchaseInvoked = false;

    @Test
    void newPurchaseUsesNewBasket() {
        PointOfSale.handleTotal(new PurchaseProvider() {
            @Override
            public void startPurchase() {
                TestSellMultipleItems.this.startPurchaseInvoked = true;
            }

            @Override
            public int getTotal() {
                // Stub to return an irrelevant value
                return -6183712;
            }

            @Override
            public void addItem(int price) {
                throw new RuntimeException("Don't invoke me");
            }
        });

        Assertions.assertEquals(true, startPurchaseInvoked);
    }
}
