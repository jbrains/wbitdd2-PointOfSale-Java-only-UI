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
    void completedPurchase() {
        final FormatTotal formatTotal = new FormatTotal(new FormatMonetaryAmount(Locale.ENGLISH));
        PurchaseAccumulator purchaseAccumulator = new PurchaseAccumulator() {
            @Override
            public Purchase completePurchase() {
                return new Purchase(0, List.of());
            }

            @Override
            public void addPriceOfScannedItemToCurrentPurchase(int price) {

            }
        };
        FormatReceipt formatReceipt = new FormatReceipt(formatTotal) {
            @Override
            public String formatReceipt(Purchase purchase) {
                return "::receipt::";
            }
        };
        assertEquals("::receipt::", new StandardPrintReceiptAction(purchaseAccumulator, formatReceipt).printReceipt());
    }

    static class FormatReceiptTest {
        @Test
        void noItems() {
            assertEquals("Total: CAD 0.00", new FormatReceipt(new FormatTotal(new FormatMonetaryAmount(Locale.ENGLISH))).formatReceipt(new Purchase(0, List.of())));
        }

        @Test
        void oneItem() {
            final FormatReceipt formatReceipt = new FormatReceipt(new FormatTotal(new FormatMonetaryAmount(Locale.ENGLISH)));
            final Purchase purchase = new Purchase(790, List.of(new CatalogEntry(Barcode.makeBarcode("12345").get(), 790)));
            assertEquals("12345          CAD 7.90" +
                    System.lineSeparator() +
                    "Total: CAD 7.90", formatReceipt.formatReceipt(purchase));
        }
    }

    public static class StandardPrintReceiptAction extends PrintReceiptAction {
        private final PurchaseAccumulator purchaseAccumulator;
        private final FormatReceipt formatReceipt;

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