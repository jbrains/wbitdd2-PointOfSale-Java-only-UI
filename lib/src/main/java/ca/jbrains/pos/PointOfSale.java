package ca.jbrains.pos;

import ca.jbrains.pos.domain.*;
import ca.jbrains.pos.stub.StubProductFound;
import io.vavr.control.Either;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.function.Function;
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
            displayToConsole(displaySellOneItem(barcode -> Either.right(new StubProductFound()), Barcode.parse(notEmptyBarcode)));
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

    public static String displaySellOneItem(BarcodeScannedHandler barcodeScannedHandler, Barcode barcode) {
        Either<ProductNotFound, ProductFound> returnProduct = barcodeScannedHandler.handleScannedBarcode(barcode);
        return returnProduct.fold(productNotFound -> String.format("Product not found: %s", returnProduct.getLeft().getBarcode()),
                productFound -> formatPrice(returnProduct.get().getPrice()));
    }

    public static String formatPrice(Price unformattedPrice) {
        return String.format("CAD %.2f", unformattedPrice.getAmount()/100d);
    }

}
