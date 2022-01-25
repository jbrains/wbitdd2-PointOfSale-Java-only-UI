package ca.jbrains.pos.test;

import ca.jbrains.pos.PointOfSale;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Locale;

public class FormatPriceTest {
    @Test
    void happyPath() {
        Assertions.assertEquals("CAD 1.00", PointOfSale.formatPrice(100));
    }

    @Test
    void anotherPrice() {
        Assertions.assertEquals("CAD 1.01", PointOfSale.formatPrice(101));
    }
}
