package ca.jbrains.pos.test;

import ca.jbrains.pos.PointOfSale;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestScanningAnItem {
    @Test
    void scanOneItem() {
        Stream<String> lines = makeBufferedReader(List.of("12345"));
        Assertions.assertEquals(List.of("12345"), lines.collect(Collectors.toList()));
    }

    private Stream<String> makeBufferedReader(List<String> barcodes) {
        Reader simulateInputFromStdin = new StringReader(barcodes.stream().collect(Collectors.joining(System.lineSeparator())));
        return PointOfSale.parseInput(simulateInputFromStdin);
    }

    @Test
    void scanMultipleItems() {
        Stream<String> lines = makeBufferedReader(List.of("12345", "678910"));
        List<String> collect = lines.collect(Collectors.toList());

        Assertions.assertEquals(List.of("12345", "678910"), collect);
    }
}
