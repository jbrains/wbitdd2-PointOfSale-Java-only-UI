package ca.jbrains.pos.domain;

import ca.jbrains.pos.Barcode;

public record CatalogEntry(Barcode barcode, int price) {
}
