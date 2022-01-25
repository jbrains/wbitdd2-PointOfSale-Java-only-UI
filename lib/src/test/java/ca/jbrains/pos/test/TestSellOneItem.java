package ca.jbrains.pos.test;

import ca.jbrains.pos.Barcode;
import ca.jbrains.pos.PointOfSale;
import ca.jbrains.pos.domain.Basket;
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
        String response = PointOfSale.handleBarcode(new Barcode("99999"), priceNotFoundCatalog,
                null);

        Assertions.assertEquals("Product not found: 99999", response);
    }

    @Test
    void givenBarcodeIs1111ShouldDisplayProductNotFoundMessage() {
        String response = PointOfSale.handleBarcode(Barcode.makeBarcode("1111").get(), priceNotFoundCatalog,
                null);

        Assertions.assertEquals("Product not found: 1111", response);
    }

    @Test
    void priceFound() {
        String response = PointOfSale.handleBarcode(Barcode.makeBarcode("99999").get(), priceFoundCatalog,
                new PurchaseProvider() {
                    @Override
                    public void startPurchase() {

                    }

                    @Override
                    public int getTotal() {
                        return 0;
                    }
                });

        Assertions.assertEquals("CAD 1.00", response);
    }

    @Test
    void addItemToBasketWhenProductIsFound() {
        RecordingPurchaseProvider purchaseProvider = new RecordingPurchaseProvider(new RecordingBasket());
        PointOfSale.handleBarcode(Barcode.makeBarcode("::any barcode::").get(), priceFoundCatalog,
                purchaseProvider);
        Assertions.assertEquals(Option.some(100), purchaseProvider.price);
    }

    private static class RecordingPurchaseProvider implements PurchaseProvider {
        private final RecordingBasket basket;
        private Option<Integer> price;

        public RecordingPurchaseProvider(RecordingBasket basket) {
            this.basket = basket;
        }

        @Override
        public void startPurchase() {

        }

        @Override
        public int getTotal() {
            return basket.getTotal();
        }

        @Override
        public void addPriceOfScannedItem(int price) {
            basket.add(price);
            this.price = Option.some(price);
        }
    }
}
