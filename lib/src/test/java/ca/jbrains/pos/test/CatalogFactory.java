package ca.jbrains.pos.test;

import ca.jbrains.pos.Barcode;
import ca.jbrains.pos.domain.Catalog;
import io.vavr.control.Either;
import io.vavr.control.Option;

public class CatalogFactory {
    static Catalog catalogWithPrice(int price) {
        return new Catalog() {
            @Override
            public Either<Barcode, Integer> findPrice(Barcode barcode) {
                return Either.right(price);
            }

        };
    }

    static Catalog priceNotFoundCatalog() {
        return new Catalog() {
            @Override
            public Either<Barcode, Integer> findPrice(Barcode barcode) {
                return Either.left(barcode);
            }

        };
    }
}