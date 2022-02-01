package ca.jbrains.pos.test;

import ca.jbrains.pos.Barcode;
import ca.jbrains.pos.PointOfSale;
import ca.jbrains.pos.domain.Catalog;
import ca.jbrains.pos.domain.PurchaseAccumulator;
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
                new PurchaseAccumulator() {
                    @Override
                    public void completePurchase() {

                    }

                    @Override
                    public int getTotalOfCurrentPurchase() {
                        return 0;
                    }

                    @Override
                    public void addPriceOfScannedItemToCurrentPurchase(int price) {

                    }
                });

        Assertions.assertEquals("CAD 1.00", response);
    }

    @Test
    void rememberTheScannedItemWhenProductIsFound() {
        RecordingPurchaseAccumulator purchaseProvider = new RecordingPurchaseAccumulator();
        PointOfSale.handleBarcode(Barcode.makeBarcode("::any barcode::").get(), priceFoundCatalog,
                purchaseProvider);
        Assertions.assertEquals(Option.some(100), purchaseProvider.price);
    }

    private static class RecordingPurchaseAccumulator implements PurchaseAccumulator {
        private Option<Integer> price;

        @Override
        public void completePurchase() {
        }

        @Override
        public int getTotalOfCurrentPurchase() {
            return -1;
        }

        @Override
        public void addPriceOfScannedItemToCurrentPurchase(int price) {
            this.price = Option.some(price);
        }
    }
}
