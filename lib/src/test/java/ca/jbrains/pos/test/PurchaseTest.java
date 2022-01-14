package ca.jbrains.pos.test;

import ca.jbrains.pos.Barcode;
import ca.jbrains.pos.PointOfSale;
import ca.jbrains.pos.domain.Basket;
import ca.jbrains.pos.domain.Catalog;
import io.vavr.control.Either;
import org.jmock.Expectations;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.util.List;
import java.util.stream.Collectors;

public class PurchaseTest {
    @RegisterExtension
    final JUnit5Mockery context = new JUnit5Mockery();
    private final Catalog catalog = context.mock(Catalog.class);

    @Test
    void oneItem() {
        context.checking(new Expectations() {{
            allowing(catalog).findPrice(with(aNonNull(Barcode.class)));
            will(returnValue(Either.right(795)));
        }});
        Basket basket = new NotEmptyBasket(795);

        // SMELL Duplicates logic in PointOfSale.runApplication(): stream lines, handle each line, consume the result
        Assertions.assertEquals(
                List.of("CAD 7.95", "Total: CAD 7.95"),
                List.of("12345", "total").stream().map(line -> PointOfSale.handleLine(line, basket, catalog)).collect(Collectors.toList()));
    }

    @Test
    void aDifferentItem() {
        context.checking(new Expectations() {{
            allowing(catalog).findPrice(with(aNonNull(Barcode.class)));
            will(returnValue(Either.right(995)));
        }});
        Basket basket = new NotEmptyBasket(995);

        // SMELL Duplicates logic in PointOfSale.runApplication(): stream lines, handle each line, consume the result
        Assertions.assertEquals(
                List.of("CAD 9.95", "Total: CAD 9.95"),
                List.of("12345", "total").stream().map(line -> PointOfSale.handleLine(line, basket, catalog)).collect(Collectors.toList()));
    }
}
