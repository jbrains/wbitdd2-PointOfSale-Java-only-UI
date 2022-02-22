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
    void printReceiptDirectlyAfterCompletingPurchase () {
        PurchaseAccumulator accumulator = new PurchaseAccumulator() {
            @Override
            public Purchase completePurchase() {
                return new Purchase(0, List.of());
            }

            @Override
            public void addPriceOfScannedItemToCurrentPurchase(int price) {

            }
        };
        FormatMonetaryAmount formatMonetaryAmount = new FormatMonetaryAmount(Locale.ENGLISH);
        PointOfSale.handleTotal(accumulator, formatMonetaryAmount);
        var result = new PrintReceiptActionTest.StandardPrintReceiptAction(accumulator, new FormatReceipt(new FormatTotal(formatMonetaryAmount))).printReceipt();

        Assertions.assertEquals("", result);
    }
}
