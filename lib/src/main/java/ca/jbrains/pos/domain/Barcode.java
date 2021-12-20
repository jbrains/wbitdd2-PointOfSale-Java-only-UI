package ca.jbrains.pos.domain;

public class Barcode {
    private final String barcodeText;

    public Barcode(String notTrustedBarcodeText) {
        barcodeText = notTrustedBarcodeText;
    }

    public static Barcode parse(String notTrustedBarcodeText) {
        return new Barcode(notTrustedBarcodeText);
    }

    public String getBarcode() {
        return barcodeText;
    }
}
