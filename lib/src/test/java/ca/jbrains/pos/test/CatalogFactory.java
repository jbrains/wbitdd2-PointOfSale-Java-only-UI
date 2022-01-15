package ca.jbrains.pos.test;

import ca.jbrains.pos.domain.Catalog;
import io.vavr.control.Either;

public class CatalogFactory {
    static Catalog catalogWithPrice(int price) {
        return barcode -> Either.right(price);
    }

    static Catalog priceNotFoundCatalog() {
        return barcode -> Either.left(barcode);
    }
}