package ca.jbrains.pos.test;

import ca.jbrains.pos.Barcode;
import ca.jbrains.pos.Catalog;
import ca.jbrains.pos.PointOfSale;
import ca.jbrains.pos.Price;
import io.vavr.control.Either;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestSellOneItem {
    @Test
    void priceNotFound() {
        Assertions.assertEquals(
                "Product not found: 99999",
                PointOfSale.handleBarcodeScanned(new Barcode("99999"), new Catalog() {
                    @Override
                    public String findFormattedPrice(String ignored) {
                        return null;
                    }

                    @Override
                    public Either<Barcode, Price> findPrice(Barcode barcode) {
                        return Either.left(barcode);
                    }
                })
        );
    }

    @Test
    void givenBarcodeIs1111ShouldDisplayProductNotFoundMessage() {
        Assertions.assertEquals(
                "Product not found: 1111",
                PointOfSale.handleBarcodeScanned(new Barcode("1111"), new Catalog() {
                    @Override
                    public String findFormattedPrice(String ignored) {
                        return null;
                    }

                    @Override
                    public Either<Barcode, Price> findPrice(Barcode barcode) {
                        return Either.left(barcode);
                    }
                })
        );
    }

    @Test
    void priceFound() {
        Assertions.assertEquals(
                "CAD 1.00",
                PointOfSale.handleBarcodeScanned(new Barcode("99999"), new Catalog() {
                    @Override
                    public String findFormattedPrice(String ignored) {
                        return "CAD 1.00";
                    }

                    @Override
                    public Either<Barcode, Price> findPrice(Barcode barcode) {
                        return Either.right(new Price(100));
                    }
                })
        );
    }
}
