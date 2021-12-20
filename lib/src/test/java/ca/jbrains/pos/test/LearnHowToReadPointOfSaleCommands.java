package ca.jbrains.pos.test;

import ca.jbrains.pos.PointOfSale;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LearnHowToReadPointOfSaleCommands {
    @Test
    void readOneCommand() {
        Stream<String> lines = PointOfSale.streamLinesFrom(readerForLines(List.of("12345")));
        Assertions.assertEquals(List.of("12345"), lines.collect(Collectors.toList()));
    }

    @Test
    void readMultipleCommands() {
        Stream<String> lines = PointOfSale.streamLinesFrom(readerForLines(List.of("12345", "678910")));
        List<String> collect = lines.collect(Collectors.toList());
        Assertions.assertEquals(List.of("12345", "678910"), collect);
    }

    private StringReader readerForLines(List<String> lines) {
        return new StringReader(multilineStringFrom(lines));
    }

    private static String multilineStringFrom(List<String> lines) {
        return lines.stream().collect(Collectors.joining(System.lineSeparator()));
    }
}
