package ca.jbrains.pos.test;

import ca.jbrains.pos.Barcode;
import ca.jbrains.pos.PointOfSale;
import ca.jbrains.pos.domain.Catalog;
import io.vavr.control.Option;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestSellOneItem {
    @Test
    void priceNotFound() {
        String response = PointOfSale.handleSellOneItemRequest(barcode -> Option.none(), new Barcode("99999"));

        Assertions.assertEquals(
                "Product not found: 99999",
                response
        );
    }

    @Test
    void givenBarcodeIs1111ShouldDisplayProductNotFoundMessage() {
        String response = PointOfSale.handleSellOneItemRequest(barcode -> Option.none(), Barcode.makeBarcode("1111").get());

        Assertions.assertEquals(
                "Product not found: 1111",
                response
        );
    }

    @Test
    void priceFound() {
        String response = PointOfSale.handleSellOneItemRequest(barcode -> Option.of(100), Barcode.makeBarcode("99999").get());

        Assertions.assertEquals(
                "CAD 1.00",
                response
        );
    }
}
