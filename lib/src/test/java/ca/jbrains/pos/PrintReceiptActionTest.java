package ca.jbrains.pos;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class PrintReceiptActionTest {
    @Disabled("Refactoring")
    @Test
    void happyPathAfterOnly1Purchase() {
        FormatMonetaryAmount formatMonetaryAmount = new FormatMonetaryAmount(Locale.ENGLISH);
        assertEquals("Total : 7.90 CAD", new PrintReceiptAction() {
            @Override
            public String printReceipt() {
                return formatMonetaryAmount.formatMonetaryAmount(790);
            }
        }.printReceipt());
    }
}