package ca.jbrains.pos.test;

import ca.jbrains.pos.FormatMonetaryAmount;
import ca.jbrains.pos.PointOfSale;
import ca.jbrains.pos.Purchase;
import ca.jbrains.pos.domain.PurchaseAccumulator;
import io.vavr.control.Option;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Locale;
import static org.mockito.Mockito.*;


public class TestSellMultipleItems {
    @Test
    void handleTotalStartsNewPurchase() {
        PurchaseAccumulator purchaseAccumulator = mock(PurchaseAccumulator.class);
        when(purchaseAccumulator.completePurchase()).thenReturn(Option.some(new Purchase(-1, Collections.emptyList())));

        PointOfSale.handleTotal(purchaseAccumulator, new FormatMonetaryAmount(new Locale("en", "US")));

        verify(purchaseAccumulator).completePurchase();
    }

}
