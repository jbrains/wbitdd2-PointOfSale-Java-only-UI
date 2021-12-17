package ca.jbrains.pos.domain;

public interface Catalog {
    Price getPrice(Barcode barcode);
}
