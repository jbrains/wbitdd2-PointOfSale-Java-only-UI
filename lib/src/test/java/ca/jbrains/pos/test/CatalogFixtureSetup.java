package ca.jbrains.pos.test;

import ca.jbrains.pos.Barcode;
import ca.jbrains.pos.Catalog;
import io.vavr.control.Either;
import org.jmock.Expectations;
import org.jmock.junit5.JUnit5Mockery;

public class CatalogFixtureSetup {

    static void notFoundCatalog(JUnit5Mockery context, Catalog catalog, Barcode barcode) {
        context.checking(new Expectations() {{
            allowing(catalog).findPrice(with(aNonNull(Barcode.class)));
            will(returnValue(Either.left(barcode)));
        }});
    }

    void catalogWithPrice(JUnit5Mockery context, int value, final Catalog catalog) {
        context.checking(new Expectations() {{
            allowing(catalog).findPrice(with(aNonNull(Barcode.class)));
            will(returnValue(Either.right(value)));
        }});
    }
}