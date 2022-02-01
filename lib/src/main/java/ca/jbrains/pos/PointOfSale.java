package ca.jbrains.pos;

import ca.jbrains.pos.domain.Catalog;
import ca.jbrains.pos.domain.PurchaseAccumulator;
import io.vavr.control.Either;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class PointOfSale {
    static {
        // SMELL Duplicates assumptions in the "format price" tests
        Locale.setDefault(new Locale("en", "US"));
    }

    public static void main(String[] args) {
        runApplication(new InputStreamReader(System.in), System.out::println);
    }

    private static void runApplication(Reader commandLinesReader, Consumer<String> consoleDisplay) {
        // SMELL Duplicates logic in PurchaseTest: stream lines, handle each line, consume the result
        streamLinesFrom(commandLinesReader)
                .map(line -> handleLine(line, createAnyCatalog(), createAnyPurchaseAccumulator()))
                .forEachOrdered(consoleDisplay);
    }

    private static PurchaseAccumulator createAnyPurchaseAccumulator() {
        return new PurchaseAccumulator() {
            @Override
            public Purchase completePurchase() {
                throw new RuntimeException("Not our job");
            }

            @Override
            public int getTotalOfCurrentPurchase() {
                throw new RuntimeException("Not our job");
            }

            @Override
            public void addPriceOfScannedItemToCurrentPurchase(int price) {
                throw new RuntimeException("Not our job");
            }
        };
    }

    private static Catalog createAnyCatalog() {
        return new Catalog() {
            @Override
            public Either<Barcode, Integer> findPrice(Barcode barcode) {
                throw new RuntimeException("Not our job");
            }
        };
    }

    public static String handleLine(String line, Catalog catalog, PurchaseAccumulator purchaseAccumulator) {
        if ("total".equals(line)) {
            return handleTotal(purchaseAccumulator);
        }

        return Barcode.makeBarcode(line)
                .map(barcode -> handleBarcode(barcode, catalog, purchaseAccumulator))
                .getOrElse("Scanning error: empty barcode");
    }

    public static Stream<String> streamLinesFrom(Reader reader) {
        return new BufferedReader(reader).lines();
    }

    public static String handleBarcode(Barcode barcode, Catalog catalog,
                                       PurchaseAccumulator purchaseAccumulator) {
        return catalog.findPrice(barcode).fold(
                PointOfSale::formatProductNotFoundMessage,
                matchingPrice -> handleProductFound(matchingPrice, purchaseAccumulator)
        );
    }

    private static String formatProductNotFoundMessage(Barcode barcode) {
        return String.format("Product not found: %s", barcode.text());
    }

    private static String handleProductFound(int price, PurchaseAccumulator purchaseAccumulator) {
        purchaseAccumulator.addPriceOfScannedItemToCurrentPurchase(price);
        return formatMonetaryAmount(price);
    }

    public static String formatMonetaryAmount(int canadianCents) {
        return String.format("CAD %.2f", canadianCents / 100.0d);
    }

    public static String handleTotal(PurchaseAccumulator purchaseAccumulator) {
        return String.format("Total: %s", formatMonetaryAmount(purchaseAccumulator.completePurchase().total()));
    }

}
