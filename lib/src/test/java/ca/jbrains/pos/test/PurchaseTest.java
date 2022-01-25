package ca.jbrains.pos.test;

import ca.jbrains.pos.PointOfSale;
import ca.jbrains.pos.domain.Basket;
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
        Basket basket = new NotEmptyBasket(795);

        // SMELL Duplicates logic in PointOfSale.runApplication(): stream lines, handle each line, consume the result
        Assertions.assertEquals(
                List.of("CAD 7.95", "Total: CAD 7.95"),
                List.of("12345", "total").stream().map(line -> PointOfSale.handleLine(line, catalog, new PurchaseProvider() {
                    @Override
                    public void startPurchase() {
                    }

                    @Override
                    public int getTotal() {
                        return basket.getTotal();
                    }
                })).collect(Collectors.toList()));
    }

    @Test
    void aDifferentItem() {
        Catalog catalog = new PriceFoundCatalog(995);
        Basket basket = new NotEmptyBasket(995);

        // SMELL Duplicates logic in PointOfSale.runApplication(): stream lines, handle each line, consume the result
        Assertions.assertEquals(
                List.of("CAD 9.95", "Total: CAD 9.95"),
                List.of("12345", "total").stream().map(line -> PointOfSale.handleLine(line, catalog, new PurchaseProvider() {
                    @Override
                    public void startPurchase() {
                    }

                    @Override
                    public int getTotal() {
                        return basket.getTotal();
                    }
                })).collect(Collectors.toList()));
    }
}
