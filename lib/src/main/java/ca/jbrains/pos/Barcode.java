package ca.jbrains.pos;

public record Barcode(String text) {
    public static Barcode parse(String text) {
        return new Barcode(text);
    }
}
