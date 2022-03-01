package ca.jbrains.pos.test;

import ca.jbrains.pos.Barcode;
import ca.jbrains.pos.Purchase;
import ca.jbrains.pos.domain.CatalogEntry;
import ca.jbrains.pos.domain.PurchaseAccumulator;
import io.vavr.control.Option;

import java.util.List;

public class PurchaseInProgressAccumulator implements PurchaseAccumulator {
    @Override
    public Purchase legacyCompletePurchase() {
        // CONTRACT: If we completed the purchase, it would contain these items.
        return new Purchase(2, List.of(new CatalogEntry(new Barcode("2"), 2)));
    }

    @Override
    public void addPriceOfScannedItemToCurrentPurchase(int price) {

    }

    @Override
    public boolean isPurchaseInProgress() {
        // CONTRACT: simulate a purchase in progress
        return true;
    }

    @Override
    public Option<Purchase> completePurchase() {
        return Option.of(legacyCompletePurchase());
    }
}
