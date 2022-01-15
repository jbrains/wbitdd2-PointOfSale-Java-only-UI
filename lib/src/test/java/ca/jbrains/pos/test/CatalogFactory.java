package ca.jbrains.pos.test;

import ca.jbrains.pos.domain.Catalog;
import io.vavr.control.Option;

public class CatalogFactory {
    static Catalog catalogWithPrice(int price) {
        return barcode -> Option.of(price);
    }

    static Catalog priceNotFoundCatalog() {
        return barcode -> Option.none();
    }
}