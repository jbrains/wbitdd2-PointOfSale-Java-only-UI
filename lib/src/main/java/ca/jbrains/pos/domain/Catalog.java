package ca.jbrains.pos.domain;

import io.vavr.control.Option;

public interface Catalog {
    // CONTRACT Assumes CAD as the currency
    Option<Integer> findPrice(String barcode);
}
