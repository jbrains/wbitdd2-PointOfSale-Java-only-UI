package ca.jbrains.pos.test;

import ca.jbrains.pos.Barcode;
import ca.jbrains.pos.PointOfSale;
import ca.jbrains.pos.domain.Basket;
import ca.jbrains.pos.domain.LegacyCatalog;
import io.vavr.control.Option;
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
    private final LegacyCatalog legacyCatalog = context.mock(LegacyCatalog.class);

    @Test
    void oneItem() {
        context.checking(new Expectations() {{
            allowing(legacyCatalog).findPrice(with(aNonNull(Barcode.class)));
            will(returnValue(Option.of(795)));
        }});
        Basket basket = new NotEmptyBasket(795);

        // SMELL Duplicates logic in PointOfSale.runApplication(): stream lines, handle each line, consume the result
        Assertions.assertEquals(
                List.of("CAD 7.95", "Total: CAD 7.95"),
                List.of("12345", "total").stream().map(line -> PointOfSale.handleLine(line, legacyCatalog, basket)).collect(Collectors.toList()));
    }

    @Test
    void aDifferentItem() {
        context.checking(new Expectations() {{
            allowing(legacyCatalog).findPrice(with(aNonNull(Barcode.class)));
            will(returnValue(Option.of(995)));
        }});
        Basket basket = new NotEmptyBasket(995);

        // SMELL Duplicates logic in PointOfSale.runApplication(): stream lines, handle each line, consume the result
        Assertions.assertEquals(
                List.of("CAD 9.95", "Total: CAD 9.95"),
                List.of("12345", "total").stream().map(line -> PointOfSale.handleLine(line, legacyCatalog, basket)).collect(Collectors.toList()));
    }
}
