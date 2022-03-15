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

public class TestTotal {
    @Test
    void noItems() {
        Assertions.assertEquals("Total: CAD 0.00", new HandleTotal(new PurchaseAccumulator() {
            @Override
            public Option<Purchase> completePurchase() {
                return Option.some(new Purchase(0, Collections.emptyList()));
            }

            @Override
            public void addPriceOfScannedItemToCurrentPurchase(int price) {
            }

            @Override
            public boolean isPurchaseInProgress() {
                return true;
            }

        }, new FormatMonetaryAmount(new Locale("en", "US"))).handleTotal());
    }

    @Test
    void oneItem() {
        Assertions.assertEquals("Total: CAD 1.02", new HandleTotal(new PurchaseAccumulator() {
            @Override
            public Option<Purchase> completePurchase() {
                return Option.some(new Purchase(102, Collections.emptyList()));
            }

            @Override
            public void addPriceOfScannedItemToCurrentPurchase(int price) {
            }

            @Override
            public boolean isPurchaseInProgress() {
                return true;
            }

        }, new FormatMonetaryAmount(new Locale("en", "US"))).handleTotal());
    }

    @Test
    void unableToCompletePurchase() {
        Assertions.assertEquals(
                "There is no purchase in progress; please scan an item.",
                new HandleTotal(new UnableToCompletePurchaseAccumulator(), null).handleTotal());
    }

    private static class UnableToCompletePurchaseAccumulator implements PurchaseAccumulator {
        @Override
        public Option<Purchase> completePurchase() {
            return Option.none();
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
