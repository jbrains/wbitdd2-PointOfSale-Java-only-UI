package ca.jbrains.pos.test;

import ca.jbrains.pos.Purchase;
import ca.jbrains.pos.domain.PurchaseAccumulator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public abstract class PurchaseAccumulatorContract {
    @Test
    void isolatePurchasesForDifferentShoppers() {
        // REFACTOR Replace "workflow" setup with "we join the program in progress" setup.
        // SMELL "Forced Workflow" problem.
        var purchaseAccumulator = purchaseAccumulatorWithAnArbitraryPurchaseInProgress();
        Purchase firstPurchase = purchaseAccumulator.completePurchase().get();

        purchaseAccumulator.addPriceOfScannedItemToCurrentPurchase(2);

        assertEquals(1, firstPurchase.total());
    }

    @Test
    void completePurchaseIsIdempotentUntilWeScanTheNextItem() {
        var purchaseAccumulator = purchaseAccumulatorWithAnArbitraryPurchaseInProgress();
        Purchase firstPurchase = purchaseAccumulator.completePurchase().get();

        // intentionally do not scan any new items
        Purchase secondPurchase = purchaseAccumulator.completePurchase().get();

        assertEquals(firstPurchase, secondPurchase);
    }

    @Test
    void afterStartingASecondPurchase() {
        var purchaseAccumulator = purchaseAccumulatorWithAnArbitraryPurchaseInProgress();
        Purchase firstPurchase = purchaseAccumulator.completePurchase().get();
        // intentionally do not scan any new items
        Purchase firstPurchaseAgain = purchaseAccumulator.completePurchase().get();
        purchaseAccumulator.addPriceOfScannedItemToCurrentPurchase(1);

        Purchase secondPurchase = purchaseAccumulator.completePurchase().get();

        assertEquals(firstPurchase, firstPurchaseAgain);
        assertNotEquals(firstPurchase, secondPurchase);
    }

    protected abstract PurchaseAccumulator purchaseAccumulatorWithAnArbitraryPurchaseInProgress();

    protected abstract PurchaseAccumulator purchaseAccumulatorWithEmptyCurrentPurchase();
}
