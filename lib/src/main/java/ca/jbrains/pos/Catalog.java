package ca.jbrains.pos;

import io.vavr.control.Either;

public interface Catalog {
    Either<Barcode, Integer> findPrice(Barcode barcode);
}
