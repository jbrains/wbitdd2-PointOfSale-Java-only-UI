package ca.jbrains.pos.test;

import ca.jbrains.pos.domain.Catalog;
import io.vavr.control.Option;

public class CatalogFactory {
    static Catalog priceNotFoundCatalog() {
        return barcode -> Option.none();
    }

    static Catalog catalogWithPrice(int value) {
        return barcode -> Option.of(value);
    }
}