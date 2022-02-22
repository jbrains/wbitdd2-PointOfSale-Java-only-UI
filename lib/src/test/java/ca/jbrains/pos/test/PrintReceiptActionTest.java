package ca.jbrains.pos.test;

import ca.jbrains.pos.*;
import ca.jbrains.pos.domain.CatalogEntry;
import ca.jbrains.pos.domain.PurchaseAccumulator;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrintReceiptActionTest {
    @Test
    void completedPurchaseWithExactlyOneItem() {
        final FormatTotal formatTotal = new FormatTotal(new FormatMonetaryAmount(Locale.ENGLISH));
        PurchaseAccumulator purchaseAccumulator = new PurchaseAccumulator() {
            @Override
            public Purchase completePurchase() {
                List<CatalogEntry> items = List.of(new CatalogEntry(Barcode.makeBarcode("12345").get(), 790));
                return new Purchase(790, items);
            }

            @Override
            public void addPriceOfScannedItemToCurrentPurchase(int price) {

            }
        };
        assertEquals("12345          CAD 7.90" +
                System.lineSeparator() +
                "Total: CAD 7.90", new StandardPrintReceiptAction(purchaseAccumulator, new FormatReceipt(formatTotal)).printReceipt());
    }

    static class FormatReceiptTest {
        @Test
        void noItems() {
            assertEquals("", new FormatReceipt(new FormatTotal(new FormatMonetaryAmount(Locale.ENGLISH))).formatReceipt(new Purchase(0, List.of())));
        }
    }

    public static class StandardPrintReceiptAction extends PrintReceiptAction {
        private final PurchaseAccumulator purchaseAccumulator;
        FormatReceipt formatReceipt;

        public StandardPrintReceiptAction(PurchaseAccumulator purchaseAccumulator, FormatReceipt formatReceipt) {
            this.purchaseAccumulator = purchaseAccumulator;
            this.formatReceipt = formatReceipt;
        }

        @Override
        public String printReceipt() {
            return formatReceipt.formatReceipt(purchaseAccumulator.completePurchase());
        }
    }
}