package ca.jbrains.pos;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class PrintReceiptActionTest {
    @Test
    void happyPathAfterOnly1Purchase() {
        FormatMonetaryAmount formatMonetaryAmount = new FormatMonetaryAmount(Locale.ENGLISH);
        final FormatTotal formatTotal = new FormatTotal(formatMonetaryAmount);
        assertEquals("Total : 7.90 CAD", new PrintReceiptAction() {
            @Override
            public String printReceipt() {
                return formatTotal.formatTotal(790);
            }
        }.printReceipt());
    }
}