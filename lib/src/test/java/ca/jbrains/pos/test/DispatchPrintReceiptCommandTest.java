package ca.jbrains.pos.test;

import ca.jbrains.pos.PointOfSale;
import ca.jbrains.pos.PrintReceiptAction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DispatchPrintReceiptCommandTest {
    private boolean printReceiptInvoked = false;

    @Test
    void happyPath() {
        PrintReceiptAction action = new PrintReceiptAction() {
            @Override
            public void printReceipt() {
                DispatchPrintReceiptCommandTest.this.printReceiptInvoked = true;
            }
        };
        PointOfSale.handleLine("receipt", null, null,null, action);
        assertEquals(true, printReceiptInvoked);
    }
}
