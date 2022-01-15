package ca.jbrains.pos.test;

import ca.jbrains.pos.Barcode;
import ca.jbrains.pos.domain.Catalog;
import io.vavr.control.Either;
import io.vavr.control.Option;

public class CatalogFactory {
    static Catalog catalogWithPrice(int price) {
        return new Catalog() {
            // REFACTOR Move into The Hole onto Catalog
            @Override
            public Either<Barcode, Integer> findProductInCatalog(Barcode barcode) {
                return findPrice(barcode).toEither(barcode);
            }

            public Option<Integer> findPrice(Barcode barcode) {
                return Option.of(price);
            }
        };
    }

    static Catalog priceNotFoundCatalog() {
        return new Catalog() {
            // REFACTOR Move into The Hole onto Catalog
            @Override
            public Either<Barcode, Integer> findProductInCatalog(Barcode barcode) {
                return findPrice(barcode).toEither(barcode);
            }

            public Option<Integer> findPrice(Barcode barcode) {
                return Option.none();
            }
        };
    }
}