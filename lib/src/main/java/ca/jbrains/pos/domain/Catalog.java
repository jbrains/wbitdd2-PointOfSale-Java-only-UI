package ca.jbrains.pos.domain;

public interface Catalog {
    Price getUnformattedPrice(Barcode barcode);
}
