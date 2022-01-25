package ca.jbrains.pos.domain;

public interface PurchaseAccumulator {
    void startNextPurchase();

    int getTotalOfCurrentPurchase();

    void addPriceOfScannedItemToCurrentPurchase(int price);
}
