package ca.jbrains.pos.test;

import ca.jbrains.pos.Barcode;
import ca.jbrains.pos.PointOfSale;
import ca.jbrains.pos.domain.Catalog;
import ca.jbrains.pos.domain.PurchaseProvider;
import io.vavr.control.Option;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestSellOneItem {
    private final Catalog priceNotFoundCatalog = new PriceNotFoundCatalog();
    private final Catalog priceFoundCatalog = new PriceFoundCatalog(100);

    @Test
    void priceNotFound() {
        String response = PointOfSale.handleSellOneItemRequest(new Barcode("99999"), priceNotFoundCatalog, null);

        Assertions.assertEquals("Product not found: 99999", response);
    }

    @Test
    void givenBarcodeIs1111ShouldDisplayProductNotFoundMessage() {
        String response = PointOfSale.handleSellOneItemRequest(Barcode.makeBarcode("1111").get(), priceNotFoundCatalog, null);

        Assertions.assertEquals("Product not found: 1111", response);
    }

    @Test
    void priceFound() {
        String response = PointOfSale.handleSellOneItemRequest(Barcode.makeBarcode("99999").get(), priceFoundCatalog, createDoNothingPurchaseProvider());

        Assertions.assertEquals("CAD 1.00", response);
    }

    public static PurchaseProvider createDoNothingPurchaseProvider() {
        return new PurchaseProvider() {
            @Override
            public void startPurchase() {
            }

            @Override
            public int getTotal() {
                return 0;
            }

            @Override
            public void addItem(int price) {
            }
        };
    }

    @Test
    void addItemToBasketWhenProductIsFound() {
        RecordingPurchaseProvider purchaseProvider = new RecordingPurchaseProvider();

        PointOfSale.handleSellOneItemRequest(Barcode.makeBarcode("::any barcode::").get(), priceFoundCatalog, purchaseProvider);

        Assertions.assertEquals(Option.some(100), purchaseProvider.priceOfMostRecentItemAdded);
    }

    private static class RecordingPurchaseProvider implements PurchaseProvider {
        public Option<Integer> priceOfMostRecentItemAdded = Option.none();

        @Override
        public void startPurchase() {
        }

        @Override
        public int getTotal() {
            return 0;
        }

        @Override
        public void addItem(int price) {
            this.priceOfMostRecentItemAdded = Option.some(price);
        }
    }
}
