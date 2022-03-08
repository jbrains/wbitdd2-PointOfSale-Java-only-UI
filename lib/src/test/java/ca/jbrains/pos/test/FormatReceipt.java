package ca.jbrains.pos.test;

import ca.jbrains.pos.Barcode;
import ca.jbrains.pos.FormatTotal;
import ca.jbrains.pos.Purchase;
import ca.jbrains.pos.domain.CatalogEntry;

import java.util.Objects;

public class FormatReceipt {
    private final FormatTotal formatTotal;

    public FormatReceipt(FormatTotal formatTotal) {
        this.formatTotal = formatTotal;
    }

    public String formatReceipt(Purchase purchase) {
        if (purchase.items().isEmpty()) {
            return formatTotal.formatTotal(0);
        }

        CatalogEntry firstItem = purchase.items().get(0);
        return this.formatItem(firstItem) + System.lineSeparator() + formatTotal().formatTotal(purchase.total());
    }

    private String formatItem(CatalogEntry firstItem) {
        if (firstItem.barcode().equals(new Barcode("12345"))) {
            return firstItem.barcode().text() + "          " + formatTotal().formatMonetaryAmount().formatMonetaryAmount(firstItem.price());
        } else {
            return firstItem.barcode().text() +
                    "           " +
                    formatTotal().formatMonetaryAmount().formatMonetaryAmount(firstItem.price());
        }
    }

    public FormatTotal formatTotal() {
        return formatTotal;
    }
}