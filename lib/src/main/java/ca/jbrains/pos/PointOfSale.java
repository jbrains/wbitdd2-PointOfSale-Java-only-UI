package ca.jbrains.pos;

import ca.jbrains.pos.domain.Catalog;
import ca.jbrains.pos.domain.PurchaseAccumulator;
import io.vavr.control.Either;
import io.vavr.control.Option;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class PointOfSale {
    public static void main(String[] args) {
        runApplication(new InputStreamReader(System.in), System.out::println);
    }

    private static void runApplication(Reader commandLinesReader, Consumer<String> consoleDisplay) {
        // SMELL Duplicates logic in PurchaseTest: stream lines, handle each line, consume the result
        streamLinesFrom(commandLinesReader)
                .map(line -> handleLine(line, null,
                        new HandleTotal(createAnyPurchaseAccumulator(),
                                createStandardFormatMonetaryAmount()), new HandleBarcode(createAnyPurchaseAccumulator(), createAnyCatalog(), createStandardFormatMonetaryAmount())))
                .forEachOrdered(consoleDisplay);
    }

    // refactor eventually wrap in a TextView class
    private static FormatMonetaryAmount createStandardFormatMonetaryAmount() {
        return new FormatMonetaryAmount(new Locale("en", "US"));
    }

    private static PurchaseAccumulator createAnyPurchaseAccumulator() {
        return new PurchaseAccumulator() {
            @Override
            public Option<Purchase> completePurchase() {
                throw notOurJob();
            }

            @Override
            public void addPriceOfScannedItemToCurrentPurchase(int price) {
                throw notOurJob();
            }

            @Override
            public boolean isPurchaseInProgress() {
                throw notOurJob();
            }
        };
    }

    private static RuntimeException notOurJob() {
        return new RuntimeException("Not our job");
    }

    private static Catalog createAnyCatalog() {
        return new Catalog() {
            @Override
            public Either<Barcode, Integer> findPrice(Barcode barcode) {
                throw notOurJob();
            }
        };
    }

    public record Request<Payload>(Controller<Payload> controller, Payload payload) {
        private String handleRequest() {
            return controller.handleRequest(payload);
        }
    }

    // REFACTOR Parse command, then execute
    public static String handleLine(String line,
                                    Controller<Void> printReceiptButtonPressedController,
                                    Controller<Void> totalButtonPressedController,
                                    Controller<Barcode> barcodeScannedController) {

        return parseRequest(line, printReceiptButtonPressedController, totalButtonPressedController, barcodeScannedController)
                .map(Request::handleRequest)
                .getOrElse("Scanning error: empty barcode");
    }

    private static Option<Request> parseRequest(String line, Controller<Void> printReceiptButtonPressedController, Controller<Void> totalButtonPressedController, Controller<Barcode> barcodeScannedController) {
        Option<Request> maybeRequest;
        if ("total".equals(line)) {
            maybeRequest = parseTotalButtonPressedRequest()
                    .map(request -> new Request(totalButtonPressedController, request));
        } else if ("receipt".equals(line)) {
            maybeRequest = parsePrintReceiptRequest()
                    .map(request -> new Request(printReceiptButtonPressedController, request));
        } else {
            maybeRequest = parseBarcodeScannedRequest(line)
                    .map(request -> new Request(barcodeScannedController, request));
        }
        return maybeRequest;
    }

    private static Option<Barcode> parseBarcodeScannedRequest(String line) {
        return Barcode.makeBarcode(line);
    }

    private static Option<Void> parsePrintReceiptRequest() {
        return Option.some(null);
    }

    private static Option<Void> parseTotalButtonPressedRequest() {
        return parsePrintReceiptRequest();
    }

    public static Stream<String> streamLinesFrom(Reader reader) {
        return new BufferedReader(reader).lines();
    }
}
