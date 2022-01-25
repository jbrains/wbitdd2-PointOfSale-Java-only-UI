package ca.jbrains.pos;

import ca.jbrains.pos.domain.Basket;
import ca.jbrains.pos.domain.Catalog;
import ca.jbrains.pos.domain.PurchaseProvider;
import io.vavr.control.Either;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class PointOfSale {
    public static void main(String[] args) {
        runApplication(new InputStreamReader(System.in), System.out::println);
    }

    private static void runApplication(Reader commandLinesReader, Consumer<String> consoleDisplay) {
        // SMELL Duplicates logic in PurchaseTest: stream lines, handle each line, consume the result
        streamLinesFrom(commandLinesReader)
                .map(line -> handleLine(line, createAnyCatalog(), createAnyBasket()))
                .forEachOrdered(consoleDisplay);
    }

    private static Catalog createAnyCatalog() {
        return new Catalog() {
            @Override
            public Either<Barcode, Integer> findPrice(Barcode barcode) {
                throw new RuntimeException("Not our job");
            }
        };
    }

    private static Basket createAnyBasket() {
        return new Basket() {
            @Override
            public void add(int price) {
                throw new RuntimeException("Not our job");
            }

            @Override
            public int getTotal() {
                throw new RuntimeException("Not our job");
            }
        };
    }

    public static String handleLine(String line, Catalog catalog, Basket basket) {
        PurchaseProvider purchaseProvider = new PurchaseProvider() {
            @Override
            public void startPurchase() {
            }

            @Override
            public int getTotal() {
                return basket.getTotal();
            }
        };
        if ("total".equals(line)) {
            return handleTotal(purchaseProvider);
        }

        return Barcode.makeBarcode(line)
                .map(barcode -> handleBarcode(barcode, catalog, basket, purchaseProvider))
                .getOrElse("Scanning error: empty barcode");
    }

    public static Stream<String> streamLinesFrom(Reader reader) {
        return new BufferedReader(reader).lines();
    }

    public static String handleBarcode(Barcode barcode, Catalog catalog, Basket basket,
        PurchaseProvider purchaseProvider) {
        return catalog.findPrice(barcode).fold(
                missingBarcode -> formatProductNotFoundMessage(missingBarcode.text()),
                matchingPrice -> addToBasketAndFormatPrice(basket, matchingPrice)
        );
    }

    private static String formatProductNotFoundMessage(String trustedBarcodeString) {
        return String.format("Product not found: %s", trustedBarcodeString);
    }

    private static String addToBasketAndFormatPrice(Basket basket, int price) {
        basket.add(price);
        return formatPrice(price);
    }

    public static String formatPrice(int priceInCanadianCents) {
        return String.format("CAD %.2f", priceInCanadianCents / 100.0d);
    }

    public static String handleTotal(PurchaseProvider purchaseProvider) {
        // SMELL Temporal coupling between these two statements.
        // DEFECT These statements are probably backwards.
        purchaseProvider.startPurchase();
        int total = purchaseProvider.getTotal();
        return String.format("Total: %s", formatPrice(total));
    }
}
