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
                .map(barcode -> new HandleBarcode(purchaseAccumulator, catalog).handleBarcode(barcode, new FormatMonetaryAmount(new Locale("en", "US"))))
                .getOrElse("Scanning error: empty barcode");
    }

    public static Stream<String> streamLinesFrom(Reader reader) {
        return new BufferedReader(reader).lines();
    }

    // SMELL jbrains: "bit strange but we leave it for now"
    public static class HandleBarcode {
        private final Catalog catalog;
        private final HandleProductFound handleProductFound;
        private final HandleProductNotFound handleProductNotFound;

        public HandleBarcode(PurchaseAccumulator purchaseAccumulator, Catalog catalog) {
            this.catalog = catalog;
            // SMELL accept them as parameter instead?
            this.handleProductFound = new HandleProductFound(purchaseAccumulator);
            this.handleProductNotFound = new HandleProductNotFound();
        }

        public String handleBarcode(Barcode barcode, FormatMonetaryAmount formatMonetaryAmount) {
            return this.catalog.findPrice(barcode).fold(
                    handleProductNotFound::handleProductNotFound,
                    price -> handleProductFound.handleProductFound(price, formatMonetaryAmount)
            );
        }
    }

    static class HandleProductNotFound {
        private String handleProductNotFound(Barcode barcode) {
            return String.format("Product not found: %s", barcode.text());
        }
    }

    public static class HandleProductFound {
        private final PurchaseAccumulator purchaseAccumulator;

        public HandleProductFound(PurchaseAccumulator purchaseAccumulator) {
            this.purchaseAccumulator = purchaseAccumulator;
        }

        public String handleProductFound(int price, FormatMonetaryAmount formatMonetaryAmount) {
            this.purchaseAccumulator.addPriceOfScannedItemToCurrentPurchase(price);
            return formatMonetaryAmount.formatMonetaryAmount(price);
        }
    }

    public static String handleTotal(PurchaseAccumulator purchaseAccumulator) {
        return String.format("Total: %s", new FormatMonetaryAmount(new Locale("en", "US")).formatMonetaryAmount(purchaseAccumulator.completePurchase().total()));
    }
}
