package ca.jbrains.pos.test;

import ca.jbrains.pos.PointOfSale;
import ca.jbrains.pos.domain.Catalog;
import io.vavr.control.Option;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestSellOneItem {
    @Test
    void priceNotFound() {
        Assertions.assertEquals(
                "Product not found: 99999",
                PointOfSale.handleSellOneItemRequest("99999", new Catalog() {

                    @Override
                    public Option<Integer> findPrice(String barcode) {
                        return Option.none();
                    }
                })
        );
    }

    @Test
    void givenBarcodeIs1111ShouldDisplayProductNotFoundMessage() {
        Assertions.assertEquals(
                "Product not found: 1111",
                PointOfSale.handleSellOneItemRequest("1111", new Catalog() {

                    @Override
                    public Option<Integer> findPrice(String barcode) {
                        return Option.none();
                    }
                })
        );
    }

    @Test
    void priceFound() {
        Assertions.assertEquals(
                "CAD 1.00",
                PointOfSale.handleSellOneItemRequest("99999", new Catalog() {

                    @Override
                    public Option<Integer> findPrice(String barcode) {
                        return Option.of(100);
                    }
                })
        );
    }

    @Test
    void givenEmptyBarcodeShouldReturnScanningErrorMessage() {
        Assertions.assertEquals(
                "Scanning error: empty barcode",
                PointOfSale.handleSellOneItemRequest("", new Catalog() {

                    @Override
                    public Option<Integer> findPrice(String barcode) {
                        return Option.none();
                    }
                })
        );
    }

}
