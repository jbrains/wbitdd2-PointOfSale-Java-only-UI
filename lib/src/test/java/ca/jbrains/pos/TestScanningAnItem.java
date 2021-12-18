package ca.jbrains.pos;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestScanningAnItem {

    public static final String BARCODE = "12345";

    @Test
    void oneBarcode() {
        Stream<String> barcodes = createStreamOfBarcodes(List.of(BARCODE));
        Assertions.assertEquals(List.of(BARCODE), barcodes.collect(Collectors.toList()));
    }

    private Stream<String> createStreamOfBarcodes(List<String> barcodes) {
        Reader barcodesFromStdin = new StringReader(barcodes.stream().collect(Collectors.joining(System.lineSeparator())));
        return PointOfSale.readBarcodesPerLine(barcodesFromStdin);
    }

    @Test
    void multipleBarcodes() {
        String OTHER_BARCODE = "678910";
        Stream<String> lines = createStreamOfBarcodes(List.of(BARCODE, OTHER_BARCODE));
        List<String> collect = lines.collect(Collectors.toList());

        Assertions.assertEquals(List.of(BARCODE, OTHER_BARCODE), collect);
    }
}
