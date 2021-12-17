package ca.jbrains.pos;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.stream.Stream;

public class PointOfSale {
    public static void main(String[] args) {
        parseInput(new InputStreamReader(System.in)).forEachOrdered(
                barcodeInput -> {
                    if ("".equals(barcodeInput)) {
                        displayToConsole(emptyBarcode());
                    } else {
                        String notEmptyBarcode = barcodeInput;
                        displayToConsole(displaySellOneItem((ignored) -> "::a hardcoded response for every barcode::", Barcode.parse(notEmptyBarcode)));
                    }
                }
        );
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

    public static String displaySellOneItem(SaleController saleController, Barcode barcode) {
        String notEmptyBarcode = barcode.getBarcode();
        String price = saleController.getPrice(notEmptyBarcode);
        if (price != null)
            return price;
        else
            return String.format("Product not found: %s", notEmptyBarcode);
    }

    public static class Barcode {
        private String barcodeText;

        public Barcode(String notTrustedBarcodeText) {
            barcodeText = notTrustedBarcodeText;
        }

        public static Barcode parse(String notTrustedBarcodeText) {
            return new Barcode(notTrustedBarcodeText);
        }

        public String getBarcode() {
            return barcodeText;
        }
    }
}
