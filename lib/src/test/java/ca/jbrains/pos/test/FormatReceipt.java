package ca.jbrains.pos.test;

import ca.jbrains.pos.FormatTotal;
import ca.jbrains.pos.Purchase;
import ca.jbrains.pos.domain.CatalogEntry;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FormatReceipt {
    private final FormatItem formatItem;
    private final FormatTotal formatTotal;

    public FormatReceipt(FormatItem formatItem, FormatTotal formatTotal) {
        this.formatItem = formatItem;
        this.formatTotal = formatTotal;
    }

    public String formatReceipt(Purchase purchase) {
        Stream<String> bodyLines = purchase.items().stream().map(item -> formatItem.formatItem(item, 30));
        Stream<String> footerLines = Stream.of(formatTotal.formatTotal(purchase.total()));
        return Stream.concat(bodyLines, footerLines).collect(Collectors.joining(System.lineSeparator()));
    }

    public String formatItem(CatalogEntry item) {
        return formatItem.formatItem(item, 30);
    }
}
