package ca.jbrains.pos.test;

import ca.jbrains.pos.FormatMonetaryAmount;
import ca.jbrains.pos.domain.CatalogEntry;

public class FormatItem {
    final FormatMonetaryAmount formatMonetaryAmount;
    final FormatBarcode formatBarcode;
    private final int lineLengthLimit;

    public FormatItem(FormatBarcode formatBarcode, FormatMonetaryAmount formatMonetaryAmount, int lineLengthLimit) {
        this.formatBarcode = formatBarcode;
        this.formatMonetaryAmount = formatMonetaryAmount;
        this.lineLengthLimit = lineLengthLimit;
    }

    public String formatItem(CatalogEntry item) {
        String barcodeText = formatBarcode.formatBarcode(item.barcode());
        String totalText = formatMonetaryAmount.formatMonetaryAmount(item.price());
        int spacesWanted = this.lineLengthLimit - (barcodeText.length() + totalText.length());
        int spaces = Math.max(0, spacesWanted);
        return barcodeText + " ".repeat(spaces) + totalText;
    }
}