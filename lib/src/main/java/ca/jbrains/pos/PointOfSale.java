package ca.jbrains.pos;

import ca.jbrains.pos.domain.Catalog;
import io.vavr.control.Option;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.stream.Stream;

public class PointOfSale {
    public static void main(String[] args) {
        // REFACTOR Replace forEach(line -> a(b(line))) with forEach(b).forEach(a)
        streamLinesFrom(new InputStreamReader(System.in)).forEachOrdered(
                line -> {
                    Option<Barcode> maybeBarcode = Barcode.makeBarcode(line);

                    String result;
                    if (maybeBarcode.isEmpty()) {
                        result = handleNoBarcode();
                    } else {
                        Barcode barcode = maybeBarcode.get();
                        result = handleBarcode(barcode);
                    }

                    displayToConsole(result);
                }
        );
    }

    private static String handleNoBarcode() {
        String result;
        result = "Scanning error: empty barcode";
        return result;
    }

    private static String handleBarcode(Barcode barcode) {
        String result;
        result = handleSellOneItemRequest(new Catalog() {
            @Override
            public Option<Integer> findPrice(String barcode) {
                return Option.of(795);
            }
        }, barcode);
        return result;
    }

    private static void displayToConsole(String message) {
        System.out.println(message);
    }

    public static Stream<String> streamLinesFrom(Reader reader) {
        return new BufferedReader(reader).lines();
    }

    public static String handleSellOneItemRequest(Catalog catalog, Barcode barcode) {
        String trustedBarcodeString = barcode.text();
        Option<Integer> unformattedPrice = catalog.findPrice(trustedBarcodeString);
        if (!unformattedPrice.isEmpty())
            return formatPrice(unformattedPrice.get());
        else
            return String.format("Product not found: %s", trustedBarcodeString);
    }

    public static String formatPrice(int priceInCanadianCents) {
        return String.format("CAD %.2f", priceInCanadianCents / 100.0d);
    }

}
