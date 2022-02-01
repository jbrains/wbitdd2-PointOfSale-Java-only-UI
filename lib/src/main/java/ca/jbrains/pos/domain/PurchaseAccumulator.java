package ca.jbrains.pos.domain;

// REFACTOR: Separate current Purchase from PurchaseHistory
public interface PurchaseAccumulator {
    // Emerging PurchaseHistory
    int completePurchase();

    int getTotalOfCurrentPurchase();

    void addPriceOfScannedItemToCurrentPurchase(int price);
}
