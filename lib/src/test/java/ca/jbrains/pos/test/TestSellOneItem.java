package ca.jbrains.pos.test;

import ca.jbrains.pos.PointOfSale;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestSellOneItem {
    @Test
    void priceNotFound() {
        Assertions.assertEquals(
                "Product not found: 99999",
                PointOfSale.displaySellOneItem("99999", (ignored) -> null)
        );
    }

    @Test
    void givenBarcodeIs1111ShouldDisplayProductNotFoundMessage() {
        Assertions.assertEquals(
                "Product not found: 1111",
                PointOfSale.displaySellOneItem("1111", (ignored) -> null)
        );
    }

    @Test
    void priceFound() {
        Assertions.assertEquals(
                "CAD 1.00",
                PointOfSale.displaySellOneItem("99999", (ignored) -> "CAD 1.00")
        );
    }

    @Test
    void givenEmptyBarcodeShouldReturnScanningErrorMessage() {
        Assertions.assertEquals(
                "Scanning error: empty barcode",
                PointOfSale.displaySellOneItem("", (ignored) -> null)
        );
    }

}
