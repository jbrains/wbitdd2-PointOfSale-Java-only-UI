package ca.jbrains.pos.test;

import ca.jbrains.pos.Catalog;
import ca.jbrains.pos.PointOfSale;
import ca.jbrains.pos.domain.LegacyCatalog;
import io.vavr.control.Option;

public class CatalogFactory {
    static Catalog catalogWithPrice(int value) {
        LegacyCatalog legacyCatalog = ignored -> Option.of(value);
        Catalog catalog = new PointOfSale.LegacyCatalogAdapter(legacyCatalog);
        return catalog;
    }

    static PointOfSale.LegacyCatalogAdapter priceNotFoundCatalog() {
        return new PointOfSale.LegacyCatalogAdapter(barcode -> Option.none());
    }
}