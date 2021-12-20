package ca.jbrains.pos;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.stream.Stream;

public class PointOfSale {
    public static void main(String[] args) {
        streamLines(new InputStreamReader(System.in)).forEachOrdered(PointOfSale::processCommand);
    }

    private static void processCommand(String line) {
        String response = handleCommand(line);
        displayToConsole(response);
    }

    private static String handleCommand(String commandText) {
        if ("".equals(commandText)) {
            return "Scanning error: empty barcode";
        } else {
            return handleBarcodeScanned(commandText, (ignored) -> "::a hardcoded response for every barcode::");
        }
    }

    private static void displayToConsole(String message) {
        System.out.println(message);
    }

    public static Stream<String> streamLines(Reader reader) {
        return new BufferedReader(reader).lines();
    }

    public static String handleBarcodeScanned(String barcode, Catalog catalog) {
        if ("".equals(barcode)) {
            return "Scanning error: empty barcode";
        }

        String formattedPrice = catalog.findFormattedPrice(barcode);
        if (formattedPrice != null)
            return formattedPrice;
        else
            return String.format("Product not found: %s", barcode);
    }
}
