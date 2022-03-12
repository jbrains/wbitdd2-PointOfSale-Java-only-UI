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
import static org.mockito.Mockito.*;

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
        PurchaseAccumulator purchaseAccumulator = mock(PurchaseAccumulator.class);

        String response = new PointOfSale.HandleBarcode(purchaseAccumulator, priceFoundCatalog,
                new FormatMonetaryAmount(new Locale("en", "US"))).handleBarcode(Barcode.makeBarcode("99999").get());

        Assertions.assertEquals("CAD 1.00", response);
    }

    @Test
    // Rename test to 'onValidBarcodePriceIsAddedToPurchase?
    void rememberTheScannedItemWhenProductIsFound() {
        PurchaseAccumulator purchaseAccumulator = mock(PurchaseAccumulator.class);

        // I tried replacing priceFoundCatalog with a test-double but couldn't get it to work
        // Lack of experience with mockito on my side rather than the library I imagine
        new PointOfSale.HandleBarcode(purchaseAccumulator, priceFoundCatalog,
                new FormatMonetaryAmount(new Locale("en", "US"))).handleBarcode(
                        Barcode.makeBarcode("::any barcode::").get());

        verify(purchaseAccumulator).addPriceOfScannedItemToCurrentPurchase(100);
    }

}
