package ca.jbrains.pos.test;

import ca.jbrains.pos.Purchase;
import ca.jbrains.pos.domain.PurchaseAccumulator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public abstract class PurchaseAccumulatorContract {
    @Test
    void isolatePurchasesForDifferentShoppers() {
        PurchaseAccumulator purchaseAccumulator = purchaseAccumulatorWithEmptyCurrentPurchase();
        purchaseAccumulator.addPriceOfScannedItemToCurrentPurchase(1);
        Purchase firstPurchase = purchaseAccumulator.completePurchase();

        purchaseAccumulator.addPriceOfScannedItemToCurrentPurchase(2);

        Assertions.assertEquals(1, firstPurchase.total());
    }

    protected abstract PurchaseAccumulator purchaseAccumulatorWithEmptyCurrentPurchase();
}
