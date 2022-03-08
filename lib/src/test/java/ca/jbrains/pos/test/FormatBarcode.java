package ca.jbrains.pos.test;

import ca.jbrains.pos.Barcode;

public class FormatBarcode {
    public FormatBarcode() {
    }

    String formatBarcode(Barcode barcode) {
        return barcode.text();
    }
}