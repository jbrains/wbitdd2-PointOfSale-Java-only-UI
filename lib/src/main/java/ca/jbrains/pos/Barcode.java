package ca.jbrains.pos;

import io.vavr.control.Option;

public record Barcode(String text) {
    public static Option<Barcode> makeBarcode(String text) {
        if ("".equals(text))
            return Option.none();
        else
            return Option.some(new Barcode(text));
    }
}
