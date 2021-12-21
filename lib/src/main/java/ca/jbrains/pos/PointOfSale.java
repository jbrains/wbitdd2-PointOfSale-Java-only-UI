package ca.jbrains.pos;

import ca.jbrains.pos.domain.Basket;
import ca.jbrains.pos.domain.Catalog;
import io.vavr.control.Option;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class PointOfSale {
    public static void main(String[] args) {
        // REFACTOR Replace forEach(line -> a(b(line))) with forEach(b).forEach(a)
        runApplication(new InputStreamReader(System.in), System.out::println);
    }

    private static void runApplication(Reader commandLinesReader, Consumer<String> consoleDisplay) {
        streamLinesFrom(commandLinesReader)
                .map(line -> handleLine(line, new Basket() {
                    @Override
                    public void add(int price) {
                    }

                    @Override
                    public int getTotal() {
                        return 0;
                    }
                }, ignored -> Option.of(795)))
                .forEachOrdered(consoleDisplay);
    }

    public static String handleLine(String line, Basket basket, Catalog catalog) {
        if ("total".equals(line)) return String.format("Total: %s", formatPrice(basket.getTotal()));

        return Barcode.makeBarcode(line)
                .map(barcode -> handleBarcode(barcode, catalog, basket))
                .getOrElse("Scanning error: empty barcode");
    }

    // REFACTOR Reorder the parameters
    private static String handleBarcode(Barcode barcode, Catalog catalog, Basket basket) {
        return handleSellOneItemRequest(catalog, basket, barcode);
    }

    public static Stream<String> streamLinesFrom(Reader reader) {
        return new BufferedReader(reader).lines();
    }

    public static String handleSellOneItemRequest(Catalog catalog, Basket basket, Barcode barcode) {
        String trustedBarcodeString = barcode.text();
        Option<Integer> unformattedPrice = catalog.findPrice(trustedBarcodeString);
        if (!unformattedPrice.isEmpty()) {
            int price = unformattedPrice.get();
            basket.add(price);
            return formatPrice(price);
        } else
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
