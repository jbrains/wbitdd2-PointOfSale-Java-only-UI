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
            String posMessage = sellItems(barcodeInput);
            displayToConsole(posMessage);
        };
    }

    private static String sellItems(String barcodeInput) {
        String posMessage;
        if ("".equals(barcodeInput)) {
            posMessage = emptyBarcode();
        } else {
            String notEmptyBarcode = barcodeInput;
            posMessage = displaySellOneItem(notEmptyBarcode, (ignored) -> "::a hardcoded response for every barcode::");
        }
        return posMessage;
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

    public static String displaySellOneItem(String untrustedBarcodeText, SaleController saleController) {
        String notEmptyBarcode = Barcode.parse(untrustedBarcodeText).getBarcode();
        String price = saleController.getPrice(notEmptyBarcode);
        if (price != null)
            return price;
        else
            return String.format("Product not found: %s", notEmptyBarcode);
    }

    private static class Barcode {
        private String barcodeText;

        public Barcode(String untrustedBarcodeText) {
            barcodeText = untrustedBarcodeText;
        }

        public static Barcode parse(String untrustedBarcodeText) {
            return new Barcode(untrustedBarcodeText);
        }

        public String getBarcode() {
            return barcodeText;
        }
    }
}
