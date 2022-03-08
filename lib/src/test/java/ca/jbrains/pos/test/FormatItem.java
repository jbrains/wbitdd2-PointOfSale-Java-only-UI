package ca.jbrains.pos.test;

import ca.jbrains.pos.FormatMonetaryAmount;
import ca.jbrains.pos.domain.CatalogEntry;

public class FormatItem {
    final FormatMonetaryAmount formatMonetaryAmount;
    final FormatBarcode formatBarcode;

    public FormatItem(FormatBarcode formatBarcode, FormatMonetaryAmount formatMonetaryAmount) {
        this.formatBarcode = formatBarcode;
        this.formatMonetaryAmount = formatMonetaryAmount;
    }

    public String formatItem(CatalogEntry item, int lineLengthLimit) {
        String barcodeText = formatBarcode.formatBarcode(item.barcode());
        String totalText = formatMonetaryAmount.formatMonetaryAmount(item.price());
        int spacesWanted = lineLengthLimit - (barcodeText.length() + totalText.length());
        int spaces = Math.max(0, spacesWanted);
        return barcodeText + " ".repeat(spaces) + totalText;
    }
}