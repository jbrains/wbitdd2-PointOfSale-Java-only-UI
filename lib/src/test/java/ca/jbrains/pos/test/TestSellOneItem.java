package ca.jbrains.pos.test;

import ca.jbrains.pos.Barcode;
import ca.jbrains.pos.PointOfSale;
import ca.jbrains.pos.Purchase;
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
        String response = new PointOfSale.HandleBarcode(null, priceNotFoundCatalog).handleBarcode(new Barcode("99999")
        );

        Assertions.assertEquals("Product not found: 99999", response);
    }

    @Test
    void givenBarcodeIs1111ShouldDisplayProductNotFoundMessage() {
        String response = new PointOfSale.HandleBarcode(null, priceNotFoundCatalog).handleBarcode(Barcode.makeBarcode("1111").get()
        );

        Assertions.assertEquals("Product not found: 1111", response);
    }

    @Test
    void priceFound() {
        String response = new PointOfSale.HandleBarcode(new PurchaseAccumulator() {
            @Override
            public Purchase completePurchase() {
                throw new UnsupportedOperationException();
            }

            @Override
            public int getTotalOfCurrentPurchase() {
                return 0;
            }

            @Override
            public void addPriceOfScannedItemToCurrentPurchase(int price) {

            }
        }, priceFoundCatalog).handleBarcode(Barcode.makeBarcode("99999").get()
        );

        Assertions.assertEquals("CAD 1.00", response);
    }

    @Test
    void rememberTheScannedItemWhenProductIsFound() {
        RecordingPurchaseAccumulator purchaseProvider = new RecordingPurchaseAccumulator();
        new PointOfSale.HandleBarcode(purchaseProvider, priceFoundCatalog).handleBarcode(Barcode.makeBarcode("::any barcode::").get()
        );
        Assertions.assertEquals(Option.some(100), purchaseProvider.price);
    }

    private static class RecordingPurchaseAccumulator implements PurchaseAccumulator {
        private Option<Integer> price;

        @Override
        public int getTotalOfCurrentPurchase() {
            return -1;
        }

        @Override
        public void addPriceOfScannedItemToCurrentPurchase(int price) {
            this.price = Option.some(price);
        }

        @Override
        public Purchase completePurchase() {
            return new Purchase(-1);
        }
    }
}
