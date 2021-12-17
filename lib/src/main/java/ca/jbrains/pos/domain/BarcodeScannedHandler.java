package ca.jbrains.pos.domain;

public interface BarcodeScannedHandler {
    Price handleScannedBarcode(Barcode barcode);
}
