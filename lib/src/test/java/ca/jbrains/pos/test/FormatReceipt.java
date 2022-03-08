package ca.jbrains.pos.test;

import ca.jbrains.pos.FormatTotal;
import ca.jbrains.pos.Purchase;
import ca.jbrains.pos.domain.CatalogEntry;

public class FormatReceipt {
    private final FormatItem formatItem;
    private final FormatTotal formatTotal;

    public FormatReceipt(FormatItem formatItem, FormatTotal formatTotal) {
        this.formatItem = formatItem;
        this.formatTotal = formatTotal;
    }

    public String formatReceipt(Purchase purchase) {
        if (purchase.items().isEmpty()) {
            return formatTotal.formatTotal(0);
        }

        CatalogEntry firstItem = purchase.items().get(0);
        return formatItem.formatItem(firstItem) + System.lineSeparator() + formatTotal.formatTotal(purchase.total());
    }

    public String formatItem(CatalogEntry item) {
        return formatItem.formatItem(item);
    }
}
