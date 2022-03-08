package ca.jbrains.pos.test;

import ca.jbrains.pos.FormatMonetaryAmount;
import ca.jbrains.pos.domain.CatalogEntry;

import java.util.Locale;

public class FormatItem {
    final FormatMonetaryAmount formatMonetaryAmount = new FormatMonetaryAmount(Locale.ENGLISH);
    final FormatBarcode formatBarcode = new FormatBarcode();

    public FormatItem() {
    }

    public String formatItem(CatalogEntry item) {
        String barcodeText = formatBarcode.formatBarcode(item.barcode());
        String totalText = formatMonetaryAmount.formatMonetaryAmount(item.price());
        int spacesWanted = 30 - (barcodeText.length() + totalText.length());
        int spaces = Math.max(0, spacesWanted);
        return barcodeText + " ".repeat(spaces) + totalText;
    }
}