package ca.jbrains.pos.domain;

import ca.jbrains.pos.Barcode;
import io.vavr.control.Either;
import io.vavr.control.Option;

public interface Catalog {
    // REFACTOR Move into The Hole onto Catalog
    static Either<Barcode, Integer> findProductInCatalog(Barcode barcode, Catalog catalog) {
        return catalog.findPrice(barcode).toEither(barcode);
    }

    // CONTRACT Assumes CAD as the currency
    Option<Integer> findPrice(Barcode barcode);
}
