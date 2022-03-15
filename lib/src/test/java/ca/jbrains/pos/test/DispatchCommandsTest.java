package ca.jbrains.pos.test;

import ca.jbrains.pos.HandleTotal;
import ca.jbrains.pos.PointOfSale;
import ca.jbrains.pos.PrintReceiptAction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DispatchCommandsTest {
    private boolean printReceiptInvoked = false;

    @Test
    void printReceiptCommand() {
        PrintReceiptAction action = new PrintReceiptAction() {
            @Override
            public String printReceipt() {
                DispatchCommandsTest.this.printReceiptInvoked = true;
                return null;
            }
        };
        PointOfSale.handleLine("receipt", action, new HandleTotal(null, null), new PointOfSale.HandleBarcode(null, null, null));
        assertEquals(true, printReceiptInvoked);
    }
}
