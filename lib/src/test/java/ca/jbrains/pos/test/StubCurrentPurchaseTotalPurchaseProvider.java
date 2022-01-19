package ca.jbrains.pos.test;

import ca.jbrains.pos.domain.PurchaseProvider;

public class StubCurrentPurchaseTotalPurchaseProvider implements PurchaseProvider {
    private int total;

    StubCurrentPurchaseTotalPurchaseProvider(int total) {
        this.total = total;
    }

    @Override
    public void startPurchase() {
    }

    @Override
    public int getTotal() {
        return total;
    }

    @Override
    public void addItem(int price) {
    }
}
