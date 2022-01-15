package ca.jbrains.pos.test;

import ca.jbrains.pos.Catalog;
import ca.jbrains.pos.PointOfSale;
import io.vavr.control.Either;
import io.vavr.control.Option;

public class CatalogFactory {
    static Catalog catalogWithPrice(int value) {
        return ignored -> Either.right(value);
    }

    static Catalog notFoundCatalog() {
        return barcode -> Either.left(barcode);
    }
}