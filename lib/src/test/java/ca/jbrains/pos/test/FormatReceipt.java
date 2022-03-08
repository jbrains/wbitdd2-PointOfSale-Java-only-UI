package ca.jbrains.pos.test;

import ca.jbrains.pos.FormatMonetaryAmount;
import ca.jbrains.pos.FormatTotal;
import ca.jbrains.pos.Purchase;
import ca.jbrains.pos.domain.CatalogEntry;

public class FormatReceipt {
    private final FormatTotal formatTotal;
    private final FormatItem formatItem = new FormatItem();

    public FormatReceipt(FormatTotal formatTotal) {
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
