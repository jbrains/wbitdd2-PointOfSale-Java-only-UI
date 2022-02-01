package ca.jbrains.pos.test;

import ca.jbrains.pos.domain.PurchaseAccumulator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public abstract class PurchaseAccumulatorContract {
    @Test
    void isolatePurchasesForDifferentShoppers() {
        PurchaseAccumulator purchaseAccumulator = purchaseAccumulatorWithEmptyCurrentPurchase();

        purchaseAccumulator.addPriceOfScannedItemToCurrentPurchase(1);
        Assertions.assertEquals(1, purchaseAccumulator.getTotalOfCurrentPurchase());

        purchaseAccumulator.startNextPurchase();
        purchaseAccumulator.addPriceOfScannedItemToCurrentPurchase(2);
        Assertions.assertEquals(2, purchaseAccumulator.getTotalOfCurrentPurchase());
    }

    protected abstract PurchaseAccumulator purchaseAccumulatorWithEmptyCurrentPurchase();
}
