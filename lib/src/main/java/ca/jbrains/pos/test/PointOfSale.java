package ca.jbrains.pos.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.stream.Stream;

public class PointOfSale {
    public static void main(String[] args) {
        readBarcodesPerLine(new InputStreamReader(System.in)).forEachOrdered(
                barcode -> displayToConsole(scanAndFetchPrice(barcode, (saleController) -> "::a hardcoded response for every barcode::"))
        );
    }

    private static void displayToConsole(String message) {
        System.out.println(message);
    }

    public static Stream<String> readBarcodesPerLine(Reader barcodesFromStdin) {
        return new BufferedReader(barcodesFromStdin).lines();
    }

    public static String scanAndFetchPrice(String barcode, SaleController saleController) {
        if ("".equals(barcode)) {
            return "Scanning error: empty barcode";
        }

        String price = saleController.fetchPrice(barcode);
        if (price != null)
            return price;
        else
            return String.format("Product not found: %s", barcode);
    }
}
