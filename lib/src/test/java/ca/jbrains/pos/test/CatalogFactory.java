package ca.jbrains.pos.test;

import ca.jbrains.pos.PointOfSale;
import io.vavr.control.Option;

public class CatalogFactory {

    static PointOfSale.LegacyCatalogAdapter notFoundCatalog() {
        return new PointOfSale.LegacyCatalogAdapter(barcode -> Option.none());
    }

    static PointOfSale.LegacyCatalogAdapter catalogWithPrice(int value) {
        return new PointOfSale.LegacyCatalogAdapter(barcode -> Option.of(value));
    }
}