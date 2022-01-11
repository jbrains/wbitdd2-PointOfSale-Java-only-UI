package ca.jbrains.pos.domain;

import ca.jbrains.pos.Barcode;
import io.vavr.control.Either;
import io.vavr.control.Option;

public interface Catalog {
    // REFACTOR Move into The Hole onto Catalog
    default Either<Barcode, Integer> findProductInCatalog(Barcode barcode) {
        return findPrice(barcode).toEither(barcode);
    }

    // CONTRACT Assumes CAD as the currency
    Option<Integer> findPrice(Barcode barcode);
}
