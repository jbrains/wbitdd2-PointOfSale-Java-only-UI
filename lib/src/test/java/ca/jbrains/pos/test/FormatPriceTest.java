package ca.jbrains.pos.test;

import ca.jbrains.pos.FormatMonetaryAmount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Locale;

public class FormatPriceTest {
    @Test
    void happyPath() {
        Assertions.assertEquals("CAD 1.00", new FormatMonetaryAmount(new Locale("en", "US")).formatMonetaryAmount(100));
    }

    @Test
    void formatUsingComma() {
        Assertions.assertEquals("CAD 1,00", new FormatMonetaryAmount(new Locale("de", "DE")).formatMonetaryAmount(100));
    }

    @Test
    void anotherPrice() {
        Assertions.assertEquals("CAD 1.01", new FormatMonetaryAmount(new Locale("en", "US")).formatMonetaryAmount(101));
    }
}
