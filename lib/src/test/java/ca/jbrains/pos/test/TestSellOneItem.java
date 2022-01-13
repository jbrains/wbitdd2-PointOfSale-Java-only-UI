package ca.jbrains.pos.test;

import ca.jbrains.pos.Barcode;
import ca.jbrains.pos.PointOfSale;
import ca.jbrains.pos.domain.Basket;
import ca.jbrains.pos.domain.Catalog;
import io.vavr.control.Either;
import io.vavr.control.Option;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TestSellOneItem {

    @Test
    void priceNotFound() {
        Catalog catalog = Mockito.spy(Catalog.class);
        Mockito.doReturn(Either.left(new Barcode("99999"))).when(catalog).findPrice(Mockito.any());

        String response = PointOfSale.handleSellOneItemRequest(new Barcode("99999"), catalog, null);

        Assertions.assertEquals("Product not found: 99999", response);
    }

    @Test
    void givenBarcodeIs1111ShouldDisplayProductNotFoundMessage() {
        Catalog catalog = Mockito.spy(Catalog.class);
        Mockito.doReturn(Either.left(new Barcode("1111"))).when(catalog).findPrice(Mockito.any());

        String response = PointOfSale.handleSellOneItemRequest(Barcode.makeBarcode("1111").get(), catalog, null);

        Assertions.assertEquals("Product not found: 1111", response);
    }

    @Test
    void priceFound() {
        Catalog catalog = Mockito.spy(Catalog.class);
        Mockito.doReturn(Either.right(100)).when(catalog).findPrice(Mockito.any());

        String response = PointOfSale.handleSellOneItemRequest(Barcode.makeBarcode("99999").get(), catalog, new DoNothingBasket());

        Assertions.assertEquals("CAD 1.00", response);
    }

    // REFACTOR Move into RecordingBasket
    private Option<Integer> addInvokedWith = Option.none();

    @Test
    void addItemToBasketWhenProductIsFound() {
        Catalog catalog = Mockito.spy(Catalog.class);
        Mockito.doReturn(Either.right(100)).when(catalog).findPrice(Mockito.any());

        Basket basket = new RecordingBasket();

        PointOfSale.handleSellOneItemRequest(Barcode.makeBarcode("::any barcode::").get(), catalog, basket);
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
