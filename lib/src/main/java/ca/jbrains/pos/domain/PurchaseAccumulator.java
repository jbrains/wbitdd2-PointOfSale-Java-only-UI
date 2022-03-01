package ca.jbrains.pos.domain;

import ca.jbrains.pos.Purchase;

// REFACTOR: Separate current Purchase from PurchaseHistory
public interface PurchaseAccumulator {
    Purchase completePurchase();

    void addPriceOfScannedItemToCurrentPurchase(int price);

    boolean isPurchaseInProgress();
}
