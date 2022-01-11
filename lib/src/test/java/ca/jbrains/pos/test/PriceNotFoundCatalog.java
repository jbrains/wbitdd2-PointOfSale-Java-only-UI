package ca.jbrains.pos.test;

import ca.jbrains.pos.Barcode;
import ca.jbrains.pos.domain.Catalog;
import io.vavr.control.Either;

public class PriceNotFoundCatalog implements Catalog {
    @Override
    public Either<Barcode, Integer> findPrice(Barcode barcode) {
        return Either.left(barcode);
    }
}
