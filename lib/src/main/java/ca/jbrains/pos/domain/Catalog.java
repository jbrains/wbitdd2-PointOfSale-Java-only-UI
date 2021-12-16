package ca.jbrains.pos.domain;

import io.vavr.control.Option;

public interface Catalog {
    // CONTRACT Assumes CAD as the currency
    // REFACTOR Replace String to Barcode
    Option<Integer> findPrice(String barcode);
}
