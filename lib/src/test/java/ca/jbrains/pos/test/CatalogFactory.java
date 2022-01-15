package ca.jbrains.pos.test;

import ca.jbrains.pos.domain.Catalog;
import io.vavr.control.Option;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class CatalogFactory {
    static Catalog priceNotFoundCatalog() {
        Catalog catalog = spy(Catalog.class);
        when(catalog.findPriceLegacy(any())).thenReturn(Option.none());
        return catalog;
    }

    static Catalog catalogWithPrice(int value) {
        Catalog catalog = spy(Catalog.class);
        when(catalog.findPriceLegacy(any())).thenReturn(Option.of(value));
        return catalog;
    }
}