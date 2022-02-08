package ca.jbrains.pos;

import java.util.Locale;

public record FormatMonetaryAmount(Locale locale) {
    public String formatMonetaryAmount(int canadianCents) {
        return String.format(locale(), "CAD %.2f", canadianCents / 100.0d);
    }
}
