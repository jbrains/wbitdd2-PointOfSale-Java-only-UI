package ca.jbrains.pos.test;

import ca.jbrains.pos.Barcode;
import ca.jbrains.pos.Catalog;
import ca.jbrains.pos.PointOfSale;
import ca.jbrains.pos.domain.Basket;
import io.vavr.control.Option;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class TestSellOneItem {
    @RegisterExtension
    final JUnit5Mockery context = new JUnit5Mockery();
    private CatalogFixtureSetup catalogFixtureSetup;
    private Catalog catalog;

    @BeforeEach
    void setUp() {
        catalogFixtureSetup = new CatalogFixtureSetup();
        catalog = context.mock(Catalog.class);
    }

    @Test
    void priceNotFound() {
        CatalogFixtureSetup.notFoundCatalog(context, catalog, new Barcode("99999"));
        String response = PointOfSale.handleSellOneItemRequest(new Barcode("99999"),
                null,
                catalog);

        Assertions.assertEquals("Product not found: 99999", response);
    }

    @Test
    void givenBarcodeIs1111ShouldDisplayProductNotFoundMessage() {
        CatalogFixtureSetup.notFoundCatalog(context, catalog, new Barcode("1111"));
        String response = PointOfSale.handleSellOneItemRequest(Barcode.makeBarcode("1111").get(),
                null,
                catalog);

        Assertions.assertEquals("Product not found: 1111", response);
    }

    @Test
    void priceFound() {
        catalogFixtureSetup.catalogWithPrice(context,
                100,
                catalog);
        String response = PointOfSale.handleSellOneItemRequest(Barcode.makeBarcode("99999").get(),
                new DoNothingBasket(),
                catalog);

        Assertions.assertEquals("CAD 1.00", response);
    }

    // REFACTOR Move into RecordingBasket
    private Option<Integer> addInvokedWith = Option.none();

    @Test
    void addItemToBasketWhenProductIsFound() {
        Basket basket = new RecordingBasket();

        PointOfSale.handleSellOneItemRequest(Barcode.makeBarcode("::any barcode::").get(), basket, new PointOfSale.LegacyCatalogAdapter(ignored -> Option.some(100)));
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
