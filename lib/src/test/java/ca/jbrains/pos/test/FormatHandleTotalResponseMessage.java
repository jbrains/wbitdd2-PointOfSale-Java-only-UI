package ca.jbrains.pos.test;

import ca.jbrains.pos.PointOfSale;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FormatHandleTotalResponseMessage {
    @Test
    void examples() {
        // Are there more examples worth checking here?
        // SMELL Why invoke handleTotal() when we're checking "format total response message"?
        Assertions.assertEquals("Total: CAD 0.00", PointOfSale.handleTotal(new StubCurrentPurchaseTotalPurchaseProvider(0)));
        Assertions.assertEquals("Total: CAD 1.02", PointOfSale.handleTotal(new StubCurrentPurchaseTotalPurchaseProvider(102)));
    }
}
