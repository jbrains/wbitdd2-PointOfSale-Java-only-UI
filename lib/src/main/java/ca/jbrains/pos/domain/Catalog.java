package ca.jbrains.pos.domain;

public interface Catalog {
    String getPrice(Barcode barcode);
    default Price getUnformattedPrice(Barcode barcode) {return new Price(100); };
}
