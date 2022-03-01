package ca.jbrains.pos.domain;

import ca.jbrains.pos.Purchase;
import io.vavr.control.Option;

// REFACTOR: Separate current Purchase from PurchaseHistory
public interface PurchaseAccumulator {

    Option<Purchase> completePurchase();

    void addPriceOfScannedItemToCurrentPurchase(int price);

    boolean isPurchaseInProgress();
}
