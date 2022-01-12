package ca.jbrains.pos.test;

import ca.jbrains.pos.Barcode;
import ca.jbrains.pos.Catalog;
import ca.jbrains.pos.PointOfSale;
import ca.jbrains.pos.domain.Basket;
import io.vavr.control.Option;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestSellOneItem {

    @Test
    void priceNotFound() {
        String response = PointOfSale.handleSellOneItemRequest(new Barcode("99999"), null, catalogFindingPrice(Option.none()));

        Assertions.assertEquals("Product not found: 99999", response);
    }

    private Catalog catalogFindingPrice(Option<Integer> maybeMatchingPrice) {
        return new PointOfSale.LegacyCatalogAdapter(barcode -> maybeMatchingPrice);
    }

    @Test
    void givenBarcodeIs1111ShouldDisplayProductNotFoundMessage() {
        String response = PointOfSale.handleSellOneItemRequest(Barcode.makeBarcode("1111").get(), null, catalogFindingPrice(Option.none()));

        Assertions.assertEquals("Product not found: 1111", response);
    }

    @Test
    void priceFound() {
        String response = PointOfSale.handleSellOneItemRequest(Barcode.makeBarcode("99999").get(), new DoNothingBasket(), catalogFindingPrice(Option.of(100)));

        Assertions.assertEquals("CAD 1.00", response);
    }

    // REFACTOR Move into RecordingBasket
    private Option<Integer> addInvokedWith = Option.none();

    @Test
    void addItemToBasketWhenProductIsFound() {
        Basket basket = new RecordingBasket();

        PointOfSale.handleSellOneItemRequest(Barcode.makeBarcode("::any barcode::").get(), basket, catalogFindingPrice(Option.some(100)));
        Assertions.assertEquals(Option.some(100), addInvokedWith);
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

    private class RecordingBasket implements Basket {
        @Override
        public void add(int price) {
            TestSellOneItem.this.addInvokedWith = Option.some(price);
        }

        @Override
        public int getTotal() {
            return 0;
        }
    }
}
