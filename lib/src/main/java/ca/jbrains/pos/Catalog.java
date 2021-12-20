package ca.jbrains.pos;

import io.vavr.control.Either;

public interface Catalog {
    String findFormattedPrice(String barcode);
    Either<Barcode, Price> findPrice(Barcode barcode);
}
