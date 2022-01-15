package ca.jbrains.pos;

import io.vavr.control.Either;

public interface Catalog {
    // REFACTOR Move into The Hole onto Catalog
    Either<Barcode, Integer> findProductInCatalog(Barcode barcode);
}
