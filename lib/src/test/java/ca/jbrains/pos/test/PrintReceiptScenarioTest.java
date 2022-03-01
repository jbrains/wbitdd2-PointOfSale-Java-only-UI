package ca.jbrains.pos.test;

import ca.jbrains.pos.Barcode;
import ca.jbrains.pos.Purchase;
import ca.jbrains.pos.domain.CatalogEntry;
import ca.jbrains.pos.domain.PurchaseAccumulator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static ca.jbrains.pos.test.PrintReceiptActionTest.StandardPrintReceiptAction;

class PrintReceiptScenarioTest {
    @Test
    void requestPrintReceiptWhileAPurchaseIsInProgress() {
        var result = new StandardPrintReceiptAction(
                new PurchaseInProgressAccumulator(), null
        ).printReceipt();

        Assertions.assertEquals("We cannot print a receipt; there is a purchase in progress.", result);
    }

    private static class PurchaseInProgressAccumulator implements PurchaseAccumulator {
        @Override
        public Purchase completePurchase() {
            // CONTRACT: If we completed the purchase, it would contain these items.
            return new Purchase(2, List.of(new CatalogEntry(new Barcode("2"), 2)));
        }

        @Override
        public void addPriceOfScannedItemToCurrentPurchase(int price) {

        }

        @Override
        public boolean isPurchaseInProgress() {
            // CONTRACT: simulate a purchase in progress
            return true;
        }
    }
}
