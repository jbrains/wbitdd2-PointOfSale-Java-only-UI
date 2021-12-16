package ca.jbrains.pos;

import ca.jbrains.pos.domain.Catalog;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.stream.Stream;

public class PointOfSale {
    public static void main(String[] args) {
        // REFACTOR Replace forEach(line -> a(b(line))) with forEach(b).forEach(a)
        streamLinesFrom(new InputStreamReader(System.in)).forEachOrdered(
                line -> displayToConsole(
                                handleSellOneItemRequest(
                                        line,
                                        (ignored) -> "::a hardcoded response for every barcode::"))
        );
    }

    private static void displayToConsole(String message) {
        System.out.println(message);
    }

    public static Stream<String> streamLinesFrom(Reader reader) {
        return new BufferedReader(reader).lines();
    }

    public static String handleSellOneItemRequest(String barcode, Catalog catalog) {
        if ("".equals(barcode)) {
            return "Scanning error: empty barcode";
        }

        String price = catalog.findPrice(barcode);
        if (price != null)
            return formatPrice(price);
        else
            return String.format("Product not found: %s", barcode);
    }

    private static String formatPrice(String price) {
        return price;
    }
}
