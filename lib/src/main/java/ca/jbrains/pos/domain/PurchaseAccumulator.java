package ca.jbrains.pos.domain;

import ca.jbrains.pos.Purchase;

// REFACTOR: Separate current Purchase from PurchaseHistory
public interface PurchaseAccumulator {
    Purchase newCompletePurchase();

    int getTotalOfCurrentPurchase();

    void addPriceOfScannedItemToCurrentPurchase(int price);
}
