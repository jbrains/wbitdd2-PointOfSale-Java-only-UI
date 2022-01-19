package ca.jbrains.pos.domain;

// CONTRACT
// keeps track of whether a Purchase has been started
// provides summary information (total cost) for Purchases
public interface PurchaseProvider {
    void startPurchase();

    int getTotal();
}
