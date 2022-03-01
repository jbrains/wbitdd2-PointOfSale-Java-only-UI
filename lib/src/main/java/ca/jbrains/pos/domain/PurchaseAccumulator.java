package ca.jbrains.pos.domain;

import ca.jbrains.pos.EmptyPurchaseHistoryException;
import ca.jbrains.pos.Purchase;

// REFACTOR: Separate current Purchase from PurchaseHistory
public interface PurchaseAccumulator {
    Purchase completePurchase() throws EmptyPurchaseHistoryException;

    void addPriceOfScannedItemToCurrentPurchase(int price);

    boolean isPurchaseInProgress();
}
