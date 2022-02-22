package ca.jbrains.pos.test;

import ca.jbrains.pos.FormatTotal;
import ca.jbrains.pos.Purchase;
import ca.jbrains.pos.domain.CatalogEntry;

public record FormatReceipt(FormatTotal formatTotal) {
     public String formatReceipt(Purchase purchase) {
        CatalogEntry firstItem = purchase.items().get(0);
        return this.formatItem(firstItem) + System.lineSeparator() + formatTotal().formatTotal(purchase.total());
    }

    private String formatItem(CatalogEntry firstItem) {
        return firstItem.barcode().text() + "          " + formatTotal().formatMonetaryAmount().formatMonetaryAmount(firstItem.price());
    }
}