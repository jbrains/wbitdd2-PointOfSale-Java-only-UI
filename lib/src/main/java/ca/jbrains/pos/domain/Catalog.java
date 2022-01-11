package ca.jbrains.pos.domain;

import ca.jbrains.pos.Barcode;
import io.vavr.control.Either;

public interface Catalog {
    Either<Barcode, Integer> findProductInCatalog(Barcode barcode);
}
