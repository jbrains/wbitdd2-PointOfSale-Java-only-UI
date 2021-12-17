package ca.jbrains.pos.test;

import ca.jbrains.pos.PointOfSale;
import ca.jbrains.pos.domain.Barcode;
import ca.jbrains.pos.domain.Catalog;
import ca.jbrains.pos.domain.Price;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestSellOneItem {
    @Test
    void priceNotFound() {
        Assertions.assertEquals(
                "Product not found: 99999",
                PointOfSale.displaySellOneItem(new Catalog() {

                    @Override
                    public Price getPrice(Barcode barcode) {
                        return null;
                    }
                }, Barcode.parse("99999"))
        );
    }

    @Test
    void givenBarcodeIs1111ShouldDisplayProductNotFoundMessage() {
        Assertions.assertEquals(
                "Product not found: 1111",
                PointOfSale.displaySellOneItem(new Catalog() {

                    @Override
                    public Price getPrice(Barcode barcode) {
                        return null;
                    }
                }, Barcode.parse("1111"))
        );
    }

    @Test
    void priceFound() {
        Assertions.assertEquals(
                "CAD 1.00",
                PointOfSale.displaySellOneItem(ignored -> new Price(100), Barcode.parse("99999"))
        );
    }

    @Test
    void shouldFormat101Correctly() {
        Assertions.assertEquals("CAD 1.01", PointOfSale.formatPrice(new Price(101)));
    }

    @Test
    void shouldFormat110Correctly() {
        Assertions.assertEquals("CAD 1.10", PointOfSale.formatPrice(new Price(110)));
    }

    @Test
    void shouldFormat100Correctly() {
        Assertions.assertEquals("CAD 1.00", PointOfSale.formatPrice(new Price(100)));
    }
}
