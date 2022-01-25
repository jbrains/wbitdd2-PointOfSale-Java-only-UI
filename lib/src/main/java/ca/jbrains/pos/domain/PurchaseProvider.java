package ca.jbrains.pos.domain;

public interface PurchaseProvider {
    void startNextPurchase();

    int getTotalOfCurrentPurchase();

    void addPriceOfScannedItemToCurrentPurchase(int price);
}
