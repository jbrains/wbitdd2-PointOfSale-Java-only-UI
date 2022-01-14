package ca.jbrains.pos.test;

import ca.jbrains.pos.Barcode;
import ca.jbrains.pos.PointOfSale;
import ca.jbrains.pos.domain.Basket;
import ca.jbrains.pos.domain.Catalog;
import io.vavr.control.Either;
import io.vavr.control.Option;
import org.jmock.Expectations;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class TestSellOneItem {
    @RegisterExtension
    final JUnit5Mockery context = new JUnit5Mockery();
    private final Catalog catalog = context.mock(Catalog.class);

    @Test
    void priceNotFound() {
        neverFindPrice(catalog, new Barcode("99999"));

        String response = PointOfSale.handleSellOneItemRequest(new Barcode("99999"), null, catalog);

        Assertions.assertEquals("Product not found: 99999", response);
    }

    private void neverFindPrice(final Catalog catalog, Barcode missingBarcode) {
        context.checking(new Expectations() {{
            allowing(catalog).findPrice(missingBarcode);
            will(returnValue(Either.left(missingBarcode)));
        }});
    }

    @Test
    void givenBarcodeIs1111ShouldDisplayProductNotFoundMessage() {
        neverFindPrice(catalog, new Barcode("1111"));

        String response = PointOfSale.handleSellOneItemRequest(Barcode.makeBarcode("1111").get(), null, catalog);

        Assertions.assertEquals("Product not found: 1111", response);
    }

    @Test
    void priceFound() {
        alwaysFindPrice(catalog, 100);

        String response = PointOfSale.handleSellOneItemRequest(Barcode.makeBarcode("99999").get(), new DoNothingBasket(), catalog);

        Assertions.assertEquals("CAD 1.00", response);
    }

    private void alwaysFindPrice(final Catalog catalog, final int matchingPrice) {
        context.checking(new Expectations() {{
            allowing(catalog).findPrice(with(aNonNull(Barcode.class)));
            will(returnValue(Either.right(matchingPrice)));
        }});
    }

    // REFACTOR Move into RecordingBasket
    private Option<Integer> addInvokedWith = Option.none();

    @Test
    void addItemToBasketWhenProductIsFound() {
        alwaysFindPrice(catalog, 100);
        Basket basket = new RecordingBasket();

        PointOfSale.handleSellOneItemRequest(Barcode.makeBarcode("::any barcode::").get(), basket, catalog);
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
