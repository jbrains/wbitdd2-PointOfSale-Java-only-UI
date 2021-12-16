package ca.jbrains.pos;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.stream.Stream;

public class PointOfSale {
    public static void main(String[] args) {
        readBarcodeFrom(new InputStreamReader(System.in)).forEachOrdered(
                line -> displayToConsole(findProductPrice(line, (ignored) -> "::a hardcoded response for every barcode::"))
        );
    }

    private static void displayToConsole(String message) {
        System.out.println(message);
    }

    public static Stream<String> readBarcodeFrom(Reader simulateInputFromStdin) {
        return new BufferedReader(simulateInputFromStdin).lines();
    }

    public static String findProductPrice(String barcode, SaleController saleController) {
        if ("".equals(barcode)) {
            return "Scanning error: empty barcode";
        }

        String price = saleController.getPrice(barcode);
        if (price != null)
            return price;
        else
            return String.format("Product not found: %s", barcode);
    }
}
