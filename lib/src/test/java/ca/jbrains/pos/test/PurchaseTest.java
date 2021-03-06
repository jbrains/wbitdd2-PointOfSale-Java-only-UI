package ca.jbrains.pos.test;

import ca.jbrains.pos.*;
import ca.jbrains.pos.domain.Catalog;
import ca.jbrains.pos.domain.PurchaseAccumulator;
import io.vavr.control.Option;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

// REFACTOR: Replace with focused tests for parsing commands in handleLine
public class PurchaseTest {

    @Test
    void oneItem() {
        Catalog catalog = new PriceFoundCatalog(795);

        // SMELL Duplicates logic in PointOfSale.runApplication(): stream lines, handle each line, consume the result
        Assertions.assertEquals(
                List.of("CAD 7.95", "Total: CAD 7.95"),
                List.of("12345", "total").stream().map(line -> {
                    final PurchaseAccumulator purchaseAccumulator = new PurchaseAccumulator() {
                        @Override
                        public Option<Purchase> completePurchase() {
                            return Option.some(new Purchase(795, Collections.emptyList()));
                        }

                        @Override
                        public void addPriceOfScannedItemToCurrentPurchase(int price) {
                        }

                        @Override
                        public boolean isPurchaseInProgress() {
                            return false;
                        }

                    };
                    final FormatMonetaryAmount formatMonetaryAmount =
                            new FormatMonetaryAmount(new Locale("en", "US"));
                    return PointOfSale.handleLine(line,
                            null,
                            new HandleTotal(purchaseAccumulator, formatMonetaryAmount), new HandleBarcode(purchaseAccumulator, catalog, formatMonetaryAmount));
                }).collect(Collectors.toList()));
    }

    @Test
    void aDifferentItem() {
        Catalog catalog = new PriceFoundCatalog(995);

        // SMELL Duplicates logic in PointOfSale.runApplication(): stream lines, handle each line, consume the result
      Assertions.assertEquals(
              List.of("CAD 9.95", "Total: CAD 9.95"),
              List.of("12345", "total").stream().map(line ->
              {
                final PurchaseAccumulator purchaseAccumulator = new PurchaseAccumulator() {
                  @Override
                  public Option<Purchase> completePurchase() {
                    return Option.some(new Purchase(995, Collections.emptyList()));
                  }

                  @Override
                  public void addPriceOfScannedItemToCurrentPurchase(int price) {
                  }

                  @Override
                  public boolean isPurchaseInProgress() {
                    return true;
                  }

                };

                final FormatMonetaryAmount formatMonetaryAmount = new FormatMonetaryAmount(
                        new Locale("en", "US"));
                return PointOfSale.handleLine(line,
                        null,
                        new HandleTotal(purchaseAccumulator, formatMonetaryAmount), new HandleBarcode(purchaseAccumulator, catalog, formatMonetaryAmount));
              }).collect(Collectors.toList()));
    }
}
