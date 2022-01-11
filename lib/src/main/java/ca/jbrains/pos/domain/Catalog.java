package ca.jbrains.pos.domain;

import ca.jbrains.pos.Barcode;
import io.vavr.control.Option;

public interface Catalog {
    // CONTRACT Assumes CAD as the currency
    Option<Integer> findPrice(Barcode barcode);
}
