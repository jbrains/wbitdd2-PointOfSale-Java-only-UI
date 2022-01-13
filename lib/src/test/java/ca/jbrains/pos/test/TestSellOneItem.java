package ca.jbrains.pos.test;

import ca.jbrains.pos.Barcode;
import ca.jbrains.pos.LegacyCatalogAdapter;
import ca.jbrains.pos.PointOfSale;
import ca.jbrains.pos.domain.Basket;
import ca.jbrains.pos.domain.LegacyCatalog;
import io.vavr.control.Option;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TestSellOneItem {

    @Test
    void priceNotFound() {
        LegacyCatalog legacyCatalog = Mockito.mock(LegacyCatalog.class);
        Mockito.when(legacyCatalog.findPrice(Mockito.any())).thenReturn(Option.none());

        String response = PointOfSale.handleSellOneItemRequest(new Barcode("99999"), null, new LegacyCatalogAdapter(legacyCatalog));

        Assertions.assertEquals("Product not found: 99999", response);
    }

    @Test
    void givenBarcodeIs1111ShouldDisplayProductNotFoundMessage() {
        LegacyCatalog legacyCatalog = Mockito.mock(LegacyCatalog.class);
        Mockito.when(legacyCatalog.findPrice(Mockito.any())).thenReturn(Option.none());

        String response = PointOfSale.handleSellOneItemRequest(Barcode.makeBarcode("1111").get(), null, new LegacyCatalogAdapter(legacyCatalog));

        Assertions.assertEquals("Product not found: 1111", response);
    }

    @Test
    void priceFound() {
        LegacyCatalog legacyCatalog = Mockito.mock(LegacyCatalog.class);
        Mockito.when(legacyCatalog.findPrice(Mockito.any())).thenReturn(Option.of(100));

        String response = PointOfSale.handleSellOneItemRequest(Barcode.makeBarcode("99999").get(), new DoNothingBasket(), new LegacyCatalogAdapter(legacyCatalog));

        Assertions.assertEquals("CAD 1.00", response);
    }

    // REFACTOR Move into RecordingBasket
    private Option<Integer> addInvokedWith = Option.none();

    @Test
    void addItemToBasketWhenProductIsFound() {
        LegacyCatalog legacyCatalog = Mockito.mock(LegacyCatalog.class);
        Mockito.when(legacyCatalog.findPrice(Mockito.any())).thenReturn(Option.some(100));

        Basket basket = new RecordingBasket();

        PointOfSale.handleSellOneItemRequest(Barcode.makeBarcode("::any barcode::").get(), basket, new LegacyCatalogAdapter(legacyCatalog));
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
