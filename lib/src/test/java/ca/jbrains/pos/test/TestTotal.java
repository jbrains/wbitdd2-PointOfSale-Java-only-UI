package ca.jbrains.pos.test;

import ca.jbrains.pos.FormatMonetaryAmount;
import ca.jbrains.pos.PointOfSale;
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
        Assertions.assertEquals("Total: CAD 0.00", PointOfSale.handleTotal(new PurchaseAccumulator() {
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

        }, new FormatMonetaryAmount(new Locale("en", "US"))));
    }

    @Test
    void oneItem() {
        Assertions.assertEquals("Total: CAD 1.02", PointOfSale.handleTotal(new PurchaseAccumulator() {
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

        }, new FormatMonetaryAmount(new Locale("en", "US"))));
    }

    @Test
    void unableToCompletePurchase() {
        Assertions.assertEquals(
                "There is no purchase in progress; please scan an item.",
                PointOfSale.handleTotal(new UnableToCompletePurchaseAccumulator(), null));
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
