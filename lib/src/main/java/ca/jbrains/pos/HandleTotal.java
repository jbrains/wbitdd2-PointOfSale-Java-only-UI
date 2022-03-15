package ca.jbrains.pos;

import ca.jbrains.pos.domain.PurchaseAccumulator;

public record HandleTotal(PurchaseAccumulator purchaseAccumulator, FormatMonetaryAmount formatMonetaryAmount) {
    public String handleTotal() {
        return purchaseAccumulator().completePurchase().fold(
                () -> "There is no purchase in progress; please scan an item.",
                (purchase) -> new FormatTotal(formatMonetaryAmount()).formatTotal(purchase.total()));
    }
}