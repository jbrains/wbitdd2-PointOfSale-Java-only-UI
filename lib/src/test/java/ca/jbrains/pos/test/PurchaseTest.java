package ca.jbrains.pos.test;

import ca.jbrains.pos.Barcode;
import ca.jbrains.pos.PointOfSale;
import ca.jbrains.pos.domain.Basket;
import ca.jbrains.pos.domain.Catalog;
import io.vavr.control.Either;
import io.vavr.control.Option;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

public class PurchaseTest {
    @Test
    void oneItem() {
        Catalog catalog = new Catalog() {
            // REFACTOR Move into The Hole onto Catalog
            @Override
            public Either<Barcode, Integer> findPrice(Barcode barcode) {
                return legacyFindPrice(barcode).toEither(barcode);
            }

            public Option<Integer> legacyFindPrice(Barcode ignored) {
                return Option.of(795);
            }
        };
        Basket basket = new NotEmptyBasket(795);

        // SMELL Duplicates logic in PointOfSale.runApplication(): stream lines, handle each line, consume the result
        Assertions.assertEquals(
                List.of("CAD 7.95", "Total: CAD 7.95"),
                List.of("12345", "total").stream().map(line -> PointOfSale.handleLine(line, catalog, basket)).collect(Collectors.toList()));
    }

    @Test
    void aDifferentItem() {
        Catalog catalog = new Catalog() {
            // REFACTOR Move into The Hole onto Catalog
            @Override
            public Either<Barcode, Integer> findPrice(Barcode barcode) {
                return legacyFindPrice(barcode).toEither(barcode);
            }

            public Option<Integer> legacyFindPrice(Barcode ignored) {
                return Option.of(995);
            }
        };
        Basket basket = new NotEmptyBasket(995);

        // SMELL Duplicates logic in PointOfSale.runApplication(): stream lines, handle each line, consume the result
        Assertions.assertEquals(
                List.of("CAD 9.95", "Total: CAD 9.95"),
                List.of("12345", "total").stream().map(line -> PointOfSale.handleLine(line, catalog, basket)).collect(Collectors.toList()));
    }
}
