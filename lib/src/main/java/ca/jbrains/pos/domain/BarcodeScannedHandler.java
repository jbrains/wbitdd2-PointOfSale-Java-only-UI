package ca.jbrains.pos.domain;

import io.vavr.control.Either;

public interface BarcodeScannedHandler {
    Either<ProductNotFound, ProductFound> handleScannedBarcode(Barcode barcode);
}
