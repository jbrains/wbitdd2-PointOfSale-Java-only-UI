package ca.jbrains.pos.test;

import io.vavr.control.Option;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BarcodeTest {
    @Test
    void invalidInput() {
        Assertions.assertEquals(Option.none(), makeBarcode(""));
    }

    private Option<Barcode> makeBarcode(String text) {
        return Option.none();
    }

    private static class Barcode {
    }
}
