package ca.jbrains.pos.test;

import ca.jbrains.pos.Barcode;
import ca.jbrains.pos.PointOfSale;
import ca.jbrains.pos.domain.Basket;
import ca.jbrains.pos.domain.Catalog;
import io.vavr.control.Option;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestSellOneItem {
    private final Catalog priceNotFoundCatalog = new PriceNotFoundCatalog();
    private final Catalog priceFoundCatalog = new PriceFoundCatalog(100);

    @Test
    void priceNotFound() {
        String response = PointOfSale.handleBarcode(new Barcode("99999"), priceNotFoundCatalog, null,
            purchaseProvider);

        Assertions.assertEquals("Product not found: 99999", response);
    }

    @Test
    void givenBarcodeIs1111ShouldDisplayProductNotFoundMessage() {
        String response = PointOfSale.handleBarcode(Barcode.makeBarcode("1111").get(), priceNotFoundCatalog, null,
            purchaseProvider);

        Assertions.assertEquals("Product not found: 1111", response);
    }

    @Test
    void priceFound() {
        String response = PointOfSale.handleBarcode(Barcode.makeBarcode("99999").get(), priceFoundCatalog, new DoNothingBasket(),
            purchaseProvider);

        Assertions.assertEquals("CAD 1.00", response);
    }

    @Test
    void addItemToBasketWhenProductIsFound() {
        RecordingBasket basket = new RecordingBasket();

        PointOfSale.handleBarcode(Barcode.makeBarcode("::any barcode::").get(), priceFoundCatalog, basket,
            purchaseProvider);
        Assertions.assertEquals(Option.some(100), basket.recentPrice);
    }

    private static class DoNothingBasket implements Basket {
        @Override
        public void add(int price) {
        }

        @Override
        public int getTotal() {
            return 0;
        }
    }

}
