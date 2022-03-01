package ca.jbrains.pos.test;

import ca.jbrains.pos.*;
import ca.jbrains.pos.domain.CatalogEntry;
import ca.jbrains.pos.domain.PurchaseAccumulator;
import io.vavr.control.Option;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrintReceiptActionTest {
    @Test
    void requestPrintReceiptWhileAPurchaseIsInProgress() {
        var result = new StandardPrintReceiptAction(
                new PurchaseInProgressAccumulator(), null
        ).printReceipt();

        assertEquals("We cannot print a receipt; there is a purchase in progress.", result);
    }

    @Test
    void completedPurchase() {
        final FormatTotal formatTotal = new FormatTotal(new FormatMonetaryAmount(Locale.ENGLISH));
        PurchaseAccumulator purchaseAccumulator = new PurchaseAccumulator() {
            @Override
            public Option<Purchase> completePurchase() {
                return Option.some(new Purchase(0, List.of()));
            }

            @Override
            public void addPriceOfScannedItemToCurrentPurchase(int price) {

            }

            @Override
            public boolean isPurchaseInProgress() {
                return false;
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

    @Test
    void noCompletedPurchaseAndNoPurchaseInProgress() {
        assertEquals("There is no completed purchase, therefore I can't print a receipt",
                new StandardPrintReceiptAction(
                        new NoHistoryPurchaseAccumulator(), new FormatReceipt(null)).printReceipt());
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
            if (purchaseAccumulator.isPurchaseInProgress())
                return "We cannot print a receipt; there is a purchase in progress.";

            Option<Purchase> completedPurchase = purchaseAccumulator.completePurchase();
            return completedPurchase.fold(() -> "There is no completed purchase, therefore I can't print a receipt", formatReceipt::formatReceipt);
        }
    }

    private static class NoHistoryPurchaseAccumulator implements PurchaseAccumulator {
        @Override
        public void addPriceOfScannedItemToCurrentPurchase(int price) {

        }

        @Override
        public boolean isPurchaseInProgress() {
            return false;
        }

        @Override
        public Option<Purchase> completePurchase() {
            return Option.none();
        }
    }
}
