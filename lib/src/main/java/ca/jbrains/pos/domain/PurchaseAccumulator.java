package ca.jbrains.pos.domain;

import ca.jbrains.pos.Purchase;

// REFACTOR: Separate current Purchase from PurchaseHistory
public interface PurchaseAccumulator {
    default Purchase newCompletePurchase() {
        return new Purchase(completePurchase());
    }

    // Emerging PurchaseHistory
    int completePurchase();

    int getTotalOfCurrentPurchase();

    void addPriceOfScannedItemToCurrentPurchase(int price);
}
