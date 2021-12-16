package ca.jbrains.pos.test;

import io.vavr.control.Option;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BarcodeTest {
    @Test
    void invalidInput() {
        Assertions.assertEquals(Option.none(), makeBarcode(""));
    }

    @Test
    void validInput() {
        Assertions.assertEquals(Option.some(new Barcode("a")), makeBarcode("a"));
    }

    private Option<Barcode> makeBarcode(String text) {
        if ("".equals(text))
            return Option.none();
        else
            return Option.some(new Barcode(text));
    }

    private record Barcode(String text) {
    }
}
