package ca.jbrains.pos.test;

import ca.jbrains.pos.PointOfSale;
import ca.jbrains.pos.domain.*;
import ca.jbrains.pos.stub.AnyProductWithPriceOf1;
import io.vavr.control.Either;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestSellOneItem {
    @Test
    void priceNotFound() {
        Assertions.assertEquals(
                "Product not found: 99999",
                PointOfSale.displaySellOneItem(barcode -> Either.left(() -> "99999"), Barcode.parse("99999"))
        );
    }

    @Test
    void givenBarcodeIs1111ShouldDisplayProductNotFoundMessage() {
        Assertions.assertEquals(
                "Product not found: 1111",
                PointOfSale.displaySellOneItem(barcode -> Either.left(() -> "1111"), Barcode.parse("1111"))
        );
    }

    @Test
    void priceFound() {
        Assertions.assertEquals(
                "CAD 1.00",
                PointOfSale.displaySellOneItem(ignored -> Either.right(new AnyProductWithPriceOf1()), Barcode.parse("99999"))
        );
    }

    @Test
    void givenLastDecimalPlaceIsNotZero_shouldDisplay2DecimalPlaces() {
        Assertions.assertEquals("CAD 1.01", PointOfSale.formatPrice(new Price(101)));
    }

    @Test
    void givenLastDecimalPlaceIsZero_shouldStillDisplayTheLastZeroDecimalPlace() {
        Assertions.assertEquals("CAD 1.10", PointOfSale.formatPrice(new Price(110)));
    }

    @Test
    void givenLast2DecimalPlacesAreZero_shouldStillDisplayTheLast2ZeroDecimalPlaces() {
        Assertions.assertEquals("CAD 1.00", PointOfSale.formatPrice(new Price(100)));
    }
}
