package ca.jbrains.pos.test;

import ca.jbrains.pos.*;
import ca.jbrains.pos.domain.CatalogEntry;
import ca.jbrains.pos.domain.PurchaseAccumulator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Locale;

public class PrintReceiptScenarioTest {
    @Test
    void requestPrintReceiptWhileAPurchaseIsInProgress() {
        PurchaseAccumulator completedPurchase = new CompletedPurchaseAccumulator();

        PurchaseAccumulator purchaseInProgress = new PurchaseInProgressAccumulator();

        FormatMonetaryAmount formatMonetaryAmount = new FormatMonetaryAmount(Locale.ENGLISH);
        PointOfSale.handleTotal(completedPurchase, formatMonetaryAmount);
        var result = new PrintReceiptActionTest.StandardPrintReceiptAction(purchaseInProgress, new FormatReceipt(new FormatTotal(formatMonetaryAmount))).printReceipt();

        Assertions.assertEquals("We cannot print a receipt; there is a purchase in progress.", result);
    }

    private static class CompletedPurchaseAccumulator implements PurchaseAccumulator {
        @Override
        public Purchase completePurchase() {
            return new Purchase(1, List.of(new CatalogEntry(new Barcode("1"), 1)));
        }

        @Override
        public void addPriceOfScannedItemToCurrentPurchase(int price) {

        }

        @Override
        public boolean isPurchaseInProgress() {
            return false;
        }
    }

    private static class PurchaseInProgressAccumulator implements PurchaseAccumulator {
        @Override
        public Purchase completePurchase() {
            return new Purchase(2, List.of(new CatalogEntry(new Barcode("2"), 2)));
        }

        @Override
        public void addPriceOfScannedItemToCurrentPurchase(int price) {

        }

        @Override
        public boolean isPurchaseInProgress() {
            return true;
        }
    }
}
