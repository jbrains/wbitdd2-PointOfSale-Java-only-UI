package ca.jbrains.pos.domain;

// CONTRACT
// keeps track of whether a Purchase has been started
// keeps track of the current Purchase items
// provides summary information (total cost) for Purchases
public interface PurchaseProvider {
    void startPurchase();

    int getTotal();

    void addItem(int price);
}
