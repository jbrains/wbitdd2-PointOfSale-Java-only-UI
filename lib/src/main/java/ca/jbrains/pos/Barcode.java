package ca.jbrains.pos;

import io.vavr.control.Option;

public record Barcode(String text) {
    public Barcode {
        if ("".equals(text)) throw new IllegalArgumentException("a Barcode's text can't be empty");
    }

    public static Option<Barcode> parse(String text) {
        try {
            return Option.some(new Barcode(text));
        } catch (IllegalArgumentException handled) {
            return Option.none();
        }
    }
}
