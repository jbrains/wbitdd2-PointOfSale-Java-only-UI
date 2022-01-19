package ca.jbrains.pos.test;

import ca.jbrains.pos.PointOfSale;
import ca.jbrains.pos.domain.Catalog;
import ca.jbrains.pos.domain.PurchaseProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

public class PurchaseTest {
    @Test
    void oneItem() {
        Catalog catalog = new PriceFoundCatalog(795);
        PurchaseProvider purchaseProvider = new StubCurrentPurchaseTotalPurchaseProvider(795);
        
        // SMELL Duplicates logic in PointOfSale.runApplication(): stream lines, handle each line, consume the result
        Assertions.assertEquals(
                List.of("CAD 7.95", "Total: CAD 7.95"),
                List.of("12345", "total").stream().map(line -> PointOfSale.handleLine(line, catalog, purchaseProvider)).collect(Collectors.toList()));
    }

    @Test
    void aDifferentItem() {
        int matchingItemPrice = 995;
        Catalog catalog = new PriceFoundCatalog(matchingItemPrice);
        int currentPurchaseTotalCost = 995;
        StubCurrentPurchaseTotalPurchaseProvider purchaseProvider = new StubCurrentPurchaseTotalPurchaseProvider(currentPurchaseTotalCost);

        // SMELL Duplicates logic in PointOfSale.runApplication(): stream lines, handle each line, consume the result
        Assertions.assertEquals(
                List.of("CAD 9.95", "Total: CAD 9.95"),
                List.of("12345", "total").stream().map(line -> PointOfSale.handleLine(line, catalog, purchaseProvider)).collect(Collectors.toList()));
    }

    private static class StubCurrentPurchaseTotalPurchaseProvider implements PurchaseProvider {
        private int total;

        private StubCurrentPurchaseTotalPurchaseProvider(int total) {
            this.total = total;
        }

        @Override
        public void startPurchase() {
        }

        @Override
        public int getTotal() {
            return total;
        }

        @Override
        public void addItem(int price) {
        }
    }
}
