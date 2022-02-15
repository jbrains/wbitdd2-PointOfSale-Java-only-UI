package ca.jbrains.pos;

import ca.jbrains.pos.domain.PurchaseAccumulator;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class PrintReceiptActionTest {
    @Test
    void happyPathAfterOnly1Purchase() {
        final FormatTotal formatTotal = new FormatTotal(new FormatMonetaryAmount(Locale.ENGLISH));
        PurchaseAccumulator purchaseAccumulator = new PurchaseAccumulator() {
            @Override
            public Purchase completePurchase() {
                return new Purchase(790);
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
                return "12345          CAD 7.90" +
                        System.lineSeparator() +
                        formatTotal.formatTotal(purchaseAccumulator.completePurchase().total());
            }
        }.printReceipt());
    }


}