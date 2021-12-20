package ca.jbrains.pos;

import io.vavr.control.Option;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.stream.Stream;

public class PointOfSale {
    public static void main(String[] args) {
        streamLines(new InputStreamReader(System.in)).forEachOrdered(PointOfSale::processCommand);
    }

    private static void processCommand(String commandText) {
        displayToConsole(handleCommand(commandText));
    }

    public static String handleCommand(String commandText) {
        Option<Barcode> parseResult = parseBarcode(commandText);

        if (parseResult.isEmpty()) {
            return "Error: empty command";
        } else {
            return handleBarcodeScanned(parseResult.get(), (ignored) -> "::a hardcoded response for every barcode::");
        }
    }

    private static Option<Barcode> parseBarcode(String commandText) {
        return "".equals(commandText) ? Option.none() : Option.some(new Barcode(commandText));
    }

    private static void displayToConsole(String message) {
        System.out.println(message);
    }

    public static Stream<String> streamLines(Reader reader) {
        return new BufferedReader(reader).lines();
    }

    // CONTRACT barcode must not be an empty string
    public static String handleBarcodeScanned(Barcode barcode, Catalog catalog) {
        String formattedPrice = catalog.findFormattedPrice(barcode.text());
        if (formattedPrice != null)
            return formattedPrice;
        else
            return String.format("Product not found: %s", barcode.text());
    }
}
