package ca.jbrains.pos.test;

import ca.jbrains.pos.Barcode;
import io.vavr.control.Option;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BarcodeTest {
    @Test
    void invalidInput() {
        Assertions.assertEquals(Option.none(), Barcode.makeBarcode(""));
    }

    @Test
    void validInput() {
        Assertions.assertEquals(Option.some(new Barcode("a")), Barcode.makeBarcode("a"));
    }

}
