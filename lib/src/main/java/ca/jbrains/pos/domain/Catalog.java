package ca.jbrains.pos.domain;

import ca.jbrains.pos.Barcode;
import io.vavr.control.Either;
import io.vavr.control.Option;

public interface Catalog {
    // CONTRACT Assumes CAD as the currency
    Either<Barcode, Integer> findPrice(Barcode barcode);
}
