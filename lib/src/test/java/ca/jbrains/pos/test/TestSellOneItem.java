package ca.jbrains.pos.test;

import ca.jbrains.pos.Barcode;
import ca.jbrains.pos.FormatMonetaryAmount;
import ca.jbrains.pos.PointOfSale;
import ca.jbrains.pos.Purchase;
import ca.jbrains.pos.domain.Catalog;
import ca.jbrains.pos.domain.PurchaseAccumulator;
import io.vavr.control.Option;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Locale;

public class TestSellOneItem {
    private final Catalog priceNotFoundCatalog = new PriceNotFoundCatalog();
    private final Catalog priceFoundCatalog = new PriceFoundCatalog(100);

    @Test
    void priceNotFound() {
        String response = new PointOfSale.HandleBarcode(null, priceNotFoundCatalog, new FormatMonetaryAmount(new Locale("en", "US"))).handleBarcode(new Barcode("99999"));

        Assertions.assertEquals("Product not found: 99999", response);
    }

    @Test
    void givenBarcodeIs1111ShouldDisplayProductNotFoundMessage() {
        String response = new PointOfSale.HandleBarcode(null, priceNotFoundCatalog, new FormatMonetaryAmount(new Locale("en", "US"))).handleBarcode(Barcode.makeBarcode("1111").get());

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
            public void addPriceOfScannedItemToCurrentPurchase(int price) {

            }
        }, priceFoundCatalog, new FormatMonetaryAmount(new Locale("en", "US"))).handleBarcode(Barcode.makeBarcode("99999").get());

        Assertions.assertEquals("CAD 1.00", response);
    }

    @Test
    void rememberTheScannedItemWhenProductIsFound() {
        RecordingPurchaseAccumulator purchaseProvider = new RecordingPurchaseAccumulator();
        new PointOfSale.HandleBarcode(purchaseProvider, priceFoundCatalog, new FormatMonetaryAmount(new Locale("en", "US"))).handleBarcode(Barcode.makeBarcode("::any barcode::").get());
        Assertions.assertEquals(Option.some(100), purchaseProvider.price);
    }

    private static class RecordingPurchaseAccumulator implements PurchaseAccumulator {
        private Option<Integer> price;

        @Override
        public void addPriceOfScannedItemToCurrentPurchase(int price) {
            this.price = Option.some(price);
        }

        @Override
        public Purchase completePurchase() {
            return new Purchase(-1, Collections.emptyList());
        }
    }
}
