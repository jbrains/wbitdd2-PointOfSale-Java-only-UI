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
                .fold(
                        () -> "Scanning error: empty barcode",
                        Request::handleRequest
                );
    }

    // REFACTOR Split into parsers using ideas from parser combinators.
    // Each individual request parser tries to parse the line, then returns Either<ParsingFailure, Request>.
    // Combine the parsers with "or".
    // The result is only a ParsingFailure if none of the parsers work.
    private static Option<Request> parseRequest(String line, Controller<Void> printReceiptButtonPressedController, Controller<Void> totalButtonPressedController, Controller<Barcode> barcodeScannedController) {
        if ("total".equals(line)) {
            return parseTotalButtonPressedRequest(line, totalButtonPressedController).toOption();
        } else if ("receipt".equals(line)) {
            return parsePrintReceiptButtonPressedRequest(line, printReceiptButtonPressedController).toOption();
        } else {
            return parseBarcodeScannedRequest(line, barcodeScannedController).toOption();
        }
    }

    public record EmptyBarcodeParsingFailure() implements ParsingFailure {}

    private static Either<ParsingFailure, Request> parseBarcodeScannedRequest(String line, Controller<Barcode> barcodeScannedController) {
        return Barcode.makeBarcode(line)
                .map(request -> new Request(barcodeScannedController, request))
                .toEither(() -> new EmptyBarcodeParsingFailure());
    }

    private static Either<ParsingFailure, Request> parsePrintReceiptButtonPressedRequest(String ignored, Controller<Void> printReceiptButtonPressedController) {
        return Either.right(new Request(printReceiptButtonPressedController, null));
    }

    private static Either<ParsingFailure, Request> parseTotalButtonPressedRequest(String ignored, Controller<Void> totalButtonPressedController) {
        return Either.right(new Request(totalButtonPressedController, null));
    }

    public static Stream<String> streamLinesFrom(Reader reader) {
        return new BufferedReader(reader).lines();
    }
}
