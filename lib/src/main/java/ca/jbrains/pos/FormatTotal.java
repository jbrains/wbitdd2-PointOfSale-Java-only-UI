package ca.jbrains.pos;

public record FormatTotal(FormatMonetaryAmount formatMonetaryAmount) {
    String formatTotal(int totalInCents) {
        return String.format("Total: %s", formatMonetaryAmount().formatMonetaryAmount(totalInCents));
    }
}