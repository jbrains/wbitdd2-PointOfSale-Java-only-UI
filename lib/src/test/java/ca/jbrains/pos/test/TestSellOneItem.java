package ca.jbrains.pos.test;

import ca.jbrains.pos.PointOfSale;
import ca.jbrains.pos.domain.Catalog;
import io.vavr.control.Option;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestSellOneItem {
    @Test
    void priceNotFound() {
        String result = PointOfSale.handleSellOneItemRequest("99999", new Catalog() {
            @Override
            public Option<Integer> findPrice(String barcode) {
                return Option.none();
            }
        });

        Assertions.assertEquals(
                "Product not found: 99999",
                result
        );
    }

    @Test
    void givenBarcodeIs1111ShouldDisplayProductNotFoundMessage() {
        String result = PointOfSale.handleSellOneItemRequest("1111", new Catalog() {
            @Override
            public Option<Integer> findPrice(String barcode) {
                return Option.none();
            }
        });
        Assertions.assertEquals(
                "Product not found: 1111",
                result
        );
    }

    @Test
    void priceFound() {
        String result = PointOfSale.handleSellOneItemRequest("99999", new Catalog() {
            @Override
            public Option<Integer> findPrice(String barcode) {
                return Option.of(100);
            }
        });

        Assertions.assertEquals(
                "CAD 1.00",
                result
        );
    }
}
