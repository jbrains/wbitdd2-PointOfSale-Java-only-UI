package ca.jbrains.pos.test;

import ca.jbrains.pos.Barcode;
import ca.jbrains.pos.FormatMonetaryAmount;
import ca.jbrains.pos.FormatTotal;
import ca.jbrains.pos.Purchase;
import ca.jbrains.pos.domain.CatalogEntry;

public class FormatReceipt {
    private final FormatTotal formatTotal;
    private final FormatMonetaryAmount formatMonetaryAmount;

    public FormatReceipt(FormatTotal formatTotal, FormatMonetaryAmount formatMonetaryAmount) {
        this.formatTotal = formatTotal;
        this.formatMonetaryAmount = formatMonetaryAmount;
    }

    public String formatReceipt(Purchase purchase) {
        if (purchase.items().isEmpty()) {
            return formatTotal.formatTotal(0);
        }

        CatalogEntry firstItem = purchase.items().get(0);
        return this.formatItem(firstItem) + System.lineSeparator() + formatTotal().formatTotal(purchase.total());
    }

    public String formatItem(CatalogEntry item) {
        String barcodeText = formatBarcode(item.barcode());
        String totalText = formatMonetaryAmount.formatMonetaryAmount(item.price());
        int spacesWanted = 30 - (barcodeText.length() + totalText.length());
        int spaces = Math.max(0, spacesWanted);
        return barcodeText + " ".repeat(spaces) + totalText;
    }

    private String formatBarcode(Barcode barcode) {
        return barcode.text();
    }

    public FormatTotal formatTotal() {
        return formatTotal;
    }
}
