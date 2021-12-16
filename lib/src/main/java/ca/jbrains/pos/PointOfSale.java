package ca.jbrains.pos;

import io.vavr.Function1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class PointOfSale {
    public static void main(String[] args) {
        parseInput(new InputStreamReader(System.in)).forEachOrdered(
                processBarcode()
        );
    }

    private static Consumer<String> processBarcode() {
        return barcodeInput -> {
            String productPrice;
            if ("".equals(barcodeInput)) {
                productPrice = emptyBarcode();
            } else {
                productPrice = displaySellOneItem(barcodeInput, (ignored) -> "::a hardcoded response for every barcode::");
            }

            displayToConsole(productPrice);
        };
    }

    private static String emptyBarcode() {
        return "Scanning error: empty barcode";
    }

    private static void displayToConsole(String message) {
        System.out.println(message);
    }

    public static Stream<String> parseInput(Reader simulateInputFromStdin) {
        return new BufferedReader(simulateInputFromStdin).lines();
    }

    public static String displaySellOneItem(String barcode, SaleController saleController) {
        String price = saleController.getPrice(barcode);
        if (price != null)
            return price;
        else
            return String.format("Product not found: %s", barcode);
    }
}
