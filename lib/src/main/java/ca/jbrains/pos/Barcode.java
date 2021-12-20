package ca.jbrains.pos;

import io.vavr.control.Option;

public record Barcode(String text) {
    public static Option<Barcode> parse(String text) {
        return "".equals(text) ? Option.none() : Option.some(new Barcode(text));
    }
}
