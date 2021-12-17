package ca.jbrains.pos.domain;

public interface Catalog {
    default Price getUnformattedPrice(Barcode barcode) {return new Price(100); };
}
