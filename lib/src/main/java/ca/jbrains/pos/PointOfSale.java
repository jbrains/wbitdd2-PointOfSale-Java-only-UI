package ca.jbrains.pos;

import ca.jbrains.pos.domain.Barcode;
import ca.jbrains.pos.domain.Catalog;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.stream.Stream;

public class PointOfSale {
    public static void main(String[] args) {
        parseInput(new InputStreamReader(System.in))
                .map(PointOfSale::parseCommand)
                .forEachOrdered(PosCommand::execute);
    }

    private static PosCommand parseCommand(String barcodeInput) {
        if ("".equals(barcodeInput)) {
            return () -> displayToConsole(emptyBarcode());
        }
        return () -> {
            String notEmptyBarcode = barcodeInput;
            displayToConsole(displaySellOneItem((ignored) -> "::a hardcoded response for every barcode::", Barcode.parse(notEmptyBarcode)));
        };
    }

    public interface PosCommand {
        void execute();
    }

    public static String emptyBarcode() {
        return "Scanning error: empty barcode";
    }

    private static void displayToConsole(String message) {
        System.out.println(message);
    }

    public static Stream<String> parseInput(Reader simulateInputFromStdin) {
        return new BufferedReader(simulateInputFromStdin).lines();
    }

    public static String displaySellOneItem(Catalog catalog, Barcode barcode) {
        String price = catalog.getPrice(barcode);
        if (price != null)
            return price;
        else
            return String.format("Product not found: %s", barcode.getBarcode());
    }

}
