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
        final Purchase completedPurchase = new Purchase(0, List.of());

        PurchaseAccumulator purchaseAccumulator = new PurchaseJustCompletedAccumulator(completedPurchase);

        FormatReceipt formatReceipt = new FormatReceipt(null, null) {
            @Override
            public String formatReceipt(Purchase purchase) {
                assertEquals(completedPurchase, purchase);
                return "::receipt::";
            }
        };

        assertEquals("::receipt::", new StandardPrintReceiptAction(purchaseAccumulator, formatReceipt).printReceipt());
    }

    @Test
    void noCompletedPurchaseAndNoPurchaseInProgress() {
        assertEquals("There is no completed purchase, therefore I can't print a receipt",
                new StandardPrintReceiptAction(
                        new NoHistoryPurchaseAccumulator(), new CrashTestDummyFormatReceipt()).printReceipt());
    }

    static class FormatReceiptTest {
        @Test
        void noItems() {
            final FormatTotal formatTotal = new FormatTotal(new FormatMonetaryAmount(Locale.ENGLISH));
            assertEquals("Total: CAD 0.00",
                    new FormatReceipt(new FormatItem(new FormatBarcode(), new FormatMonetaryAmount(Locale.ENGLISH)), formatTotal).formatReceipt(new Purchase(0, List.of())));
        }

        @Test
        void oneItem() {
            final FormatTotal formatTotal = new FormatTotal(new FormatMonetaryAmount(Locale.ENGLISH));
            final FormatReceipt formatReceipt = new FormatReceipt(new FormatItem(new FormatBarcode(), new FormatMonetaryAmount(Locale.ENGLISH)), formatTotal);
            final Purchase purchase = new Purchase(790, List.of(new CatalogEntry(Barcode.makeBarcode("12345").get(), 790)));
            assertEquals("""
                    12345                 CAD 7.90
                    Total: CAD 7.90""", formatReceipt.formatReceipt(purchase));
        }

        @Test
        void anItemRequiringADifferentNumberOfSpacesBetweenBarcodeAndPrice() {
            final FormatTotal formatTotal = new FormatTotal(new FormatMonetaryAmount(Locale.ENGLISH));
            final FormatReceipt formatReceipt = new FormatReceipt(new FormatItem(new FormatBarcode(), new FormatMonetaryAmount(Locale.ENGLISH)), formatTotal);
            final Purchase purchase = new Purchase(10_000, List.of(new CatalogEntry(Barcode.makeBarcode("12").get(), 10_000)));
            assertEquals("""
                    12                  CAD 100.00
                    Total: CAD 100.00""", formatReceipt.formatReceipt(purchase));
        }

        @Test
        void severalItems() {
            final FormatTotal formatTotal = new FormatTotal(new FormatMonetaryAmount(Locale.ENGLISH));
            final FormatReceipt formatReceipt = new FormatReceipt(new FormatItem(new FormatBarcode(), new FormatMonetaryAmount(Locale.ENGLISH)), formatTotal);
            final Purchase purchase = new Purchase(33_000,
                    List.of(new CatalogEntry(Barcode.makeBarcode("12").get(), 10_000),
                            new CatalogEntry(Barcode.makeBarcode("13").get(), 11_000),
                            new CatalogEntry(Barcode.makeBarcode("14").get(), 12_000)));
            assertEquals("""
                    12                  CAD 100.00
                    13                  CAD 110.00
                    14                  CAD 120.00
                    Total: CAD 330.00""", formatReceipt.formatReceipt(purchase));
        }

        static class FormatItemTest {
            @Test
            void noSpacesBetweenBarcodeAndPrice() {
                final FormatReceipt formatReceipt = new FormatReceipt(new FormatItem(new FormatBarcode(), new FormatMonetaryAmount(Locale.ENGLISH)), null);

                final CatalogEntry item = new CatalogEntry(Barcode.makeBarcode("12345678901234567890").get(), 10_000);
                String formattedItem = formatReceipt.formatItem(item);
                assertEquals("12345678901234567890CAD 100.00", formattedItem);
            }

            @Test
            void itemTextIsTooLong() {
                final FormatReceipt formatReceipt = new FormatReceipt(new FormatItem(new FormatBarcode(), new FormatMonetaryAmount(Locale.ENGLISH)), null);

                final CatalogEntry item = new CatalogEntry(Barcode.makeBarcode("12345678901234567890X").get(), 10_000);
                String formattedItem = formatReceipt.formatItem(item);
                assertEquals("12345678901234567890XCAD 100.00", formattedItem);
            }
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

    private static class CrashTestDummyFormatReceipt extends FormatReceipt {
        public CrashTestDummyFormatReceipt() {
            super(null, null);
        }

        @Override
        public String formatReceipt(Purchase purchase) {
            throw new UnsupportedOperationException("Don't invoke me.");
        }
    }

    private class PurchaseJustCompletedAccumulator implements PurchaseAccumulator {
        private Purchase completedPurchase;

        private PurchaseJustCompletedAccumulator(Purchase completedPurchase) {
            this.completedPurchase = completedPurchase;
        }

        @Override
        public Option<Purchase> completePurchase() {
            return Option.some(completedPurchase);
        }

        @Override
        public void addPriceOfScannedItemToCurrentPurchase(int price) {
        }

        @Override
        public boolean isPurchaseInProgress() {
            return false;
        }
    }
}
