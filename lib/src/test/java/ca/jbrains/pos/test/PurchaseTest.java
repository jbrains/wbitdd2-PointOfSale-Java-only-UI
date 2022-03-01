package ca.jbrains.pos.test;

import ca.jbrains.pos.FormatMonetaryAmount;
import ca.jbrains.pos.PointOfSale;
import ca.jbrains.pos.Purchase;
import ca.jbrains.pos.domain.Catalog;
import ca.jbrains.pos.domain.PurchaseAccumulator;
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
                List.of("12345", "total").stream().map(line -> PointOfSale.handleLine(line, catalog, new PurchaseAccumulator() {
                    @Override
                    public Purchase completePurchase() {
                        return new Purchase(795, Collections.emptyList());
                    }

                    @Override
                    public void addPriceOfScannedItemToCurrentPurchase(int price) {
                    }

                    @Override
                    public boolean isPurchaseInProgress() {
                        return false;
                    }

                }, new FormatMonetaryAmount(new Locale("en", "US")), null)).collect(Collectors.toList()));
    }

    @Test
    void aDifferentItem() {
        Catalog catalog = new PriceFoundCatalog(995);

        // SMELL Duplicates logic in PointOfSale.runApplication(): stream lines, handle each line, consume the result
        Assertions.assertEquals(
                List.of("CAD 9.95", "Total: CAD 9.95"),
                List.of("12345", "total").stream().map(line -> PointOfSale.handleLine(line, catalog, new PurchaseAccumulator() {
                    @Override
                    public Purchase completePurchase() {
                        return new Purchase(995, Collections.emptyList());
                    }

                    @Override
                    public void addPriceOfScannedItemToCurrentPurchase(int price) {
                    }

                    @Override
                    public boolean isPurchaseInProgress() {
                        return true;
                    }

                }, new FormatMonetaryAmount(new Locale("en", "US")), null)).collect(Collectors.toList()));
    }
}
