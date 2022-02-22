package ca.jbrains.pos.test;

import ca.jbrains.pos.FormatMonetaryAmount;
import ca.jbrains.pos.FormatTotal;
import ca.jbrains.pos.PrintReceiptAction;
import ca.jbrains.pos.Purchase;
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
                List<CatalogEntry> items = List.of(new CatalogEntry(null, 790));
                return new Purchase(790, items);
            }

            @Override
            public void addPriceOfScannedItemToCurrentPurchase(int price) {

            }
        };
        assertEquals("12345          CAD 7.90" +
                System.lineSeparator() +
                "Total: CAD 7.90", new PrintReceiptAction() {
            @Override
            public String printReceipt() {
                Purchase purchase = purchaseAccumulator.completePurchase();
                return "12345          " +
                        formatTotal.formatMonetaryAmount().formatMonetaryAmount(purchase.items().get(0).price()) +
                        System.lineSeparator() +
                        formatTotal.formatTotal(purchase.total());
            }
        }.printReceipt());
    }
}