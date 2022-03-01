package ca.jbrains.pos.domain;

import ca.jbrains.pos.Purchase;
import io.vavr.control.Option;

// REFACTOR: Separate current Purchase from PurchaseHistory
public interface PurchaseAccumulator {
    default Purchase legacyCompletePurchase() {
        return completePurchase().get();
    }

    Option<Purchase> completePurchase();

    void addPriceOfScannedItemToCurrentPurchase(int price);

    boolean isPurchaseInProgress();
}
