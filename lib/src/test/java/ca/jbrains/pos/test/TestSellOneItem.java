package ca.jbrains.pos.test;

import ca.jbrains.pos.*;
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
        String response = new HandleBarcode(null, priceNotFoundCatalog, new FormatMonetaryAmount(new Locale("en", "US"))).handleRequest(new Barcode("99999"));

        Assertions.assertEquals("Product not found: 99999", response);
    }

    @Test
    void givenBarcodeIs1111ShouldDisplayProductNotFoundMessage() {
        String response = new HandleBarcode(null, priceNotFoundCatalog, new FormatMonetaryAmount(new Locale("en", "US"))).handleRequest(Barcode.makeBarcode("1111").get());

        Assertions.assertEquals("Product not found: 1111", response);
    }

    @Test
    void priceFound() {
        String response = new HandleBarcode(new PurchaseAccumulator() {
            @Override
            public Option<Purchase> completePurchase() {
                throw new UnsupportedOperationException();
            }

            @Override
            public void addPriceOfScannedItemToCurrentPurchase(int price) {

            }

            @Override
            public boolean isPurchaseInProgress() {
                throw new UnsupportedOperationException();
            }
        }, priceFoundCatalog, new FormatMonetaryAmount(new Locale("en", "US"))).handleRequest(Barcode.makeBarcode("99999").get());

        Assertions.assertEquals("CAD 1.00", response);
    }

    @Test
    void rememberTheScannedItemWhenProductIsFound() {
        RecordingPurchaseAccumulator purchaseProvider = new RecordingPurchaseAccumulator();
        new HandleBarcode(purchaseProvider, priceFoundCatalog, new FormatMonetaryAmount(new Locale("en", "US"))).handleRequest(Barcode.makeBarcode("::any barcode::").get());
        Assertions.assertEquals(Option.some(100), purchaseProvider.price);
    }

    private static class RecordingPurchaseAccumulator implements PurchaseAccumulator {
        private Option<Integer> price;

        @Override
        public void addPriceOfScannedItemToCurrentPurchase(int price) {
            this.price = Option.some(price);
        }

        @Override
        public boolean isPurchaseInProgress() {
            return price.isDefined();
        }

        @Override
        public Option<Purchase> completePurchase() {
            return Option.some(new Purchase(-1, Collections.emptyList()));
        }
    }
}
