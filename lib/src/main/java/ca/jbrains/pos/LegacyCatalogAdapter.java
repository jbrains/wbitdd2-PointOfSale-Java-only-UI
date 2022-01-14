package ca.jbrains.pos;

import ca.jbrains.pos.domain.Catalog;
import ca.jbrains.pos.domain.LegacyCatalog;
import io.vavr.control.Either;

public final class LegacyCatalogAdapter implements Catalog {
    private final LegacyCatalog legacyCatalog;

    public LegacyCatalogAdapter(LegacyCatalog legacyCatalog) {
        this.legacyCatalog = legacyCatalog;
    }

    // REFACTOR Move into The Hole onto Catalog
    @Override
    public Either<Barcode, Integer> findPrice(Barcode barcode) {
        return legacyCatalog.findPrice(barcode).toEither(barcode);
    }
}