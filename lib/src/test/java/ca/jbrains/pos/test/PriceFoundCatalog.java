package ca.jbrains.pos.test;

import ca.jbrains.pos.Barcode;
import ca.jbrains.pos.domain.Catalog;
import io.vavr.control.Either;
import io.vavr.control.Option;

public class PriceFoundCatalog implements Catalog {
    private final int value;

    public PriceFoundCatalog(int value) {
        this.value = value;
    }

    @Override
    public Either<Barcode, Integer> findPrice(Barcode barcode) {
        return Either.right(value);
    }
}
