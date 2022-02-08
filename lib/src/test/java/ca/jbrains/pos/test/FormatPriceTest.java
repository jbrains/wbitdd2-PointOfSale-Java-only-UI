package ca.jbrains.pos.test;

import ca.jbrains.pos.PointOfSale;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Locale;

public class FormatPriceTest {
    @Test
    void happyPath() {
        Assertions.assertEquals("CAD 1.00", PointOfSale.formatMonetaryAmount(100, new Locale("en", "US")));
    }

    @Test
    void formatUsingComma() {
        Assertions.assertEquals("CAD 1,00", PointOfSale.formatMonetaryAmount(100, new Locale("de", "DE")));
    }

    @Test
    void anotherPrice() {
        Assertions.assertEquals("CAD 1.01", PointOfSale.formatMonetaryAmount(101, new Locale("en", "US")));
    }
}
