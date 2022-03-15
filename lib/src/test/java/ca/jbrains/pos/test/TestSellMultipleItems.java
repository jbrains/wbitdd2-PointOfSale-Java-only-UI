package ca.jbrains.pos.test;

import ca.jbrains.pos.FormatMonetaryAmount;
import ca.jbrains.pos.HandleTotal;
import ca.jbrains.pos.Purchase;
import ca.jbrains.pos.domain.PurchaseAccumulator;
import io.vavr.control.Option;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Locale;

public class TestSellMultipleItems {
    private boolean startPurchaseInvoked = false;

    @Test
    void handleTotalStartsNewPurchase() {
        new HandleTotal(new PurchaseAccumulator() {
            @Override
            public Option<Purchase> completePurchase() {
                TestSellMultipleItems.this.startPurchaseInvoked = true;
                return Option.some(new Purchase(-1, Collections.emptyList()));
            }

            @Override
            public void addPriceOfScannedItemToCurrentPurchase(int price) {

            }

            @Override
            public boolean isPurchaseInProgress() {
                return TestSellMultipleItems.this.startPurchaseInvoked;
            }
        }, new FormatMonetaryAmount(new Locale("en", "US"))).handleTotal();

        Assertions.assertEquals(true, startPurchaseInvoked);
    }

}
