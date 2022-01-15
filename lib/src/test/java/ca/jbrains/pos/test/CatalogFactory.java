package ca.jbrains.pos.test;

import ca.jbrains.pos.PointOfSale;
import io.vavr.control.Option;

public class CatalogFactory {
    static PointOfSale.LegacyCatalogAdapter catalogWithPrice(int value) {
        return new PointOfSale.LegacyCatalogAdapter(ignored -> Option.of(value));
    }
}