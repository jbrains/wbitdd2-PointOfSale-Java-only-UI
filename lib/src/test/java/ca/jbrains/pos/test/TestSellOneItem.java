package ca.jbrains.pos.test;

import ca.jbrains.pos.PointOfSale;
import ca.jbrains.pos.domain.Barcode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestSellOneItem {
    @Test
    void priceNotFound() {
        Assertions.assertEquals(
                "Product not found: 99999",
                PointOfSale.displaySellOneItem((ignored) -> null, Barcode.parse("99999"))
        );
    }

    @Test
    void givenBarcodeIs1111ShouldDisplayProductNotFoundMessage() {
        Assertions.assertEquals(
                "Product not found: 1111",
                PointOfSale.displaySellOneItem((ignored) -> null, Barcode.parse("1111"))
        );
    }

    @Test
    void priceFound() {
        Assertions.assertEquals(
                "CAD 1.00",
                PointOfSale.displaySellOneItem((ignored) -> "CAD 1.00", Barcode.parse("99999"))
        );
    }
}
