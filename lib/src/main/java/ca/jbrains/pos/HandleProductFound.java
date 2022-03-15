package ca.jbrains.pos;

import ca.jbrains.pos.domain.PurchaseAccumulator;

public class HandleProductFound {
    private final PurchaseAccumulator purchaseAccumulator;

    public HandleProductFound(PurchaseAccumulator purchaseAccumulator) {
        this.purchaseAccumulator = purchaseAccumulator;
    }

    public String handleProductFound(int price, FormatMonetaryAmount formatMonetaryAmount) {
        this.purchaseAccumulator.addPriceOfScannedItemToCurrentPurchase(price);
        return formatMonetaryAmount.formatMonetaryAmount(price);
    }
}
