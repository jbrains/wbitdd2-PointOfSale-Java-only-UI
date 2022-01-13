package ca.jbrains.pos.test;

import ca.jbrains.pos.Barcode;
import ca.jbrains.pos.PointOfSale;
import ca.jbrains.pos.domain.Basket;
import ca.jbrains.pos.domain.Catalog;
import io.vavr.control.Either;
import io.vavr.control.Option;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestSellOneItem {

    @Test
    void priceNotFound() {
        String response = PointOfSale.handleSellOneItemRequest(new Barcode("99999"), new Catalog() {
            // REFACTOR Move into The Hole onto Catalog
            @Override
            public Either<Barcode, Integer> findPrice(Barcode barcode) {
                return Option.<Integer>none().toEither(barcode);
            }
        }, null);

        Assertions.assertEquals("Product not found: 99999", response);
    }

    @Test
    void givenBarcodeIs1111ShouldDisplayProductNotFoundMessage() {
        String response = PointOfSale.handleSellOneItemRequest(Barcode.makeBarcode("1111").get(), new Catalog() {
            // REFACTOR Move into The Hole onto Catalog
            @Override
            public Either<Barcode, Integer> findPrice(Barcode barcode) {
                return Option.<Integer>none().toEither(barcode);
            }
        }, null);

        Assertions.assertEquals("Product not found: 1111", response);
    }

    @Test
    void priceFound() {
        String response = PointOfSale.handleSellOneItemRequest(Barcode.makeBarcode("99999").get(), new Catalog() {
            // REFACTOR Move into The Hole onto Catalog
            @Override
            public Either<Barcode, Integer> findPrice(Barcode barcode) {
                return Option.of(100).toEither(barcode);
            }
        }, new DoNothingBasket());

        Assertions.assertEquals("CAD 1.00", response);
    }

    // REFACTOR Move into RecordingBasket
    private Option<Integer> addInvokedWith = Option.none();

    @Test
    void addItemToBasketWhenProductIsFound() {
        Basket basket = new RecordingBasket();

        PointOfSale.handleSellOneItemRequest(Barcode.makeBarcode("::any barcode::").get(), new Catalog() {
            // REFACTOR Move into The Hole onto Catalog
            @Override
            public Either<Barcode, Integer> findPrice(Barcode barcode) {
                return Option.some(100).toEither(barcode);
            }
        }, basket);
        Assertions.assertEquals(Option.some(100), addInvokedWith);
    }

    private static class DoNothingBasket implements Basket {
        @Override
        public void add(int price) {
        }

        @Override
        public int getTotal() {
            return 0;
        }
    }

    private class RecordingBasket implements Basket {
        @Override
        public void add(int price) {
            TestSellOneItem.this.addInvokedWith = Option.some(price);
        }

        @Override
        public int getTotal() {
            return 0;
        }
    }
}
