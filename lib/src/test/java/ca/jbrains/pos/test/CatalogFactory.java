package ca.jbrains.pos.test;

import ca.jbrains.pos.Barcode;
import ca.jbrains.pos.Catalog;
import io.vavr.control.Either;
import org.mockito.stubbing.Answer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CatalogFactory {
    static Catalog catalogWithPrice(int value) {
        Catalog catalog = mock(Catalog.class);
        when(catalog.findPrice(any()))
                .thenReturn(Either.right(value));
        return catalog;
    }

    static Catalog priceNotFoundCatalog() {
        Catalog catalog = mock(Catalog.class);
        when(catalog.findPrice(any()))
                .thenAnswer((Answer<Either<Barcode, Integer>>) invocation -> Either.left(invocation.getArgument(0)));
        return catalog;
    }
}