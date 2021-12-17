package ca.jbrains.pos;

import ca.jbrains.pos.domain.Barcode;
import ca.jbrains.pos.domain.Catalog;
import ca.jbrains.pos.domain.Price;

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
        Price unformattedPrice = catalog.getUnformattedPrice(barcode);
        if (price != null && unformattedPrice != null)
            return formatPrice(price, unformattedPrice);
        else
            return String.format("Product not found: %s", barcode.getBarcode());
    }

    public static String formatPrice(String price, Price unformattedPrice) {
        return String.format("CAD %.2f", unformattedPrice.getAmount()/100d);
    }

}
