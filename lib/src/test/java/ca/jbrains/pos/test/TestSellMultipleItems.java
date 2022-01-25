package ca.jbrains.pos.test;

import ca.jbrains.pos.PointOfSale;
import ca.jbrains.pos.domain.PurchaseProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestSellMultipleItems {
    private boolean startPurchaseInvoked = false;

    @Test
    void handleTotalStartsNewPurchase() {
        PointOfSale.handleTotal(new PurchaseProvider() {
            @Override
            public void startNextPurchase() {
                TestSellMultipleItems.this.startPurchaseInvoked = true;
            }

            @Override
            public int getTotalOfCurrentPurchase() {
                return -1;
            }

            @Override
            public void addPriceOfScannedItemToCurrentPurchase(int price) {

            }
        });

        Assertions.assertEquals(true, startPurchaseInvoked);
    }
}
