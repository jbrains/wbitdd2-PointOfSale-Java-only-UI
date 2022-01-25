package ca.jbrains.pos.domain;

public interface PurchaseProvider {
    void startPurchase();

    int getTotal();

    void addPriceOfScannedItem(int price);
}
