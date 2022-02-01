package ca.jbrains.pos.test;

import ca.jbrains.pos.PointOfSale;
import ca.jbrains.pos.Purchase;
import ca.jbrains.pos.domain.PurchaseAccumulator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestSellMultipleItems {
    private boolean startPurchaseInvoked = false;

    @Test
    void handleTotalStartsNewPurchase() {
        PointOfSale.handleTotal(new PurchaseAccumulator() {
            @Override
            public Purchase completePurchase() {
                TestSellMultipleItems.this.startPurchaseInvoked = true;
                return new Purchase(-1);
            }

            @Override
            public void addPriceOfScannedItemToCurrentPurchase(int price) {

            }
        });

        Assertions.assertEquals(true, startPurchaseInvoked);
    }

}
