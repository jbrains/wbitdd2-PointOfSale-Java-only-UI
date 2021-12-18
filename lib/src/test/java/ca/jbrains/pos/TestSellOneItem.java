package ca.jbrains.pos;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestSellOneItem {
    @Test
    void notKnownProductShouldReturnProductNotFound() {
        Assertions.assertEquals(
                "Product not found: 99999",
                PointOfSale.scanAndFetchPrice("99999", (saleController) -> null)
        );
    }

    @Test
    void forOtherNotKnownProductShouldReturnProductNotFound() {
        Assertions.assertEquals(
                "Product not found: 1111",
                PointOfSale.scanAndFetchPrice("1111", (saleController) -> null)
        );
    }

    @Test
    void forKnownProductShouldReturnPrice() {
        Assertions.assertEquals(
                "CAD 1.00",
                PointOfSale.scanAndFetchPrice("99999", (saleController) -> "CAD 1.00")
        );
    }

    @Test
    void forEmptyBarcodeShouldReturnScanningError() {
        String emptyBarcode = "";

        Assertions.assertEquals(
                "Scanning error: empty barcode",
                PointOfSale.scanAndFetchPrice(emptyBarcode, (saleController) -> null)
        );
    }

}
