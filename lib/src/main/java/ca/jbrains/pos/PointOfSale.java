package ca.jbrains.pos;

import ca.jbrains.pos.domain.Basket;
import ca.jbrains.pos.domain.Catalog;
import io.vavr.control.Option;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.stream.Stream;

public class PointOfSale {
    public static void main(String[] args) {
        // REFACTOR Replace forEach(line -> a(b(line))) with forEach(b).forEach(a)
        streamLinesFrom(new InputStreamReader(System.in))
                .map(PointOfSale::handleLine)
                .forEachOrdered(System.out::println);
    }

    private static String handleLine(String line) {
        return Barcode.makeBarcode(line)
                .map(PointOfSale::handleBarcode)
                .getOrElse("Scanning error: empty barcode");
    }

    private static String handleBarcode(Barcode barcode) {
        return handleSellOneItemRequest(ignored -> Option.of(795), barcode);
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

    public static String handleTotal(Basket basket) {
        int total = basket.getTotal();
        return String.format("Total: %s", formatPrice(total));
    }
}
