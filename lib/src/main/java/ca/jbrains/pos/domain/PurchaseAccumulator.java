package ca.jbrains.pos.domain;

// REFACTOR: Separate current Purchase from PurchaseHistory
public interface PurchaseAccumulator {
    // Emerging PurchaseHistory
    void completePurchase();

    int getTotalOfCurrentPurchase();

    void addPriceOfScannedItemToCurrentPurchase(int price);
}
