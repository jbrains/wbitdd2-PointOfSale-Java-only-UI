package ca.jbrains.pos.test;

import ca.jbrains.pos.LegacyCatalogAdapter;
import ca.jbrains.pos.PointOfSale;
import ca.jbrains.pos.domain.Basket;
import ca.jbrains.pos.domain.LegacyCatalog;
import io.vavr.control.Option;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.stream.Collectors;

public class PurchaseTest {
    @Test
    void oneItem() {
        LegacyCatalog legacyCatalog = Mockito.mock(LegacyCatalog.class);
        Mockito.when(legacyCatalog.findPrice(Mockito.any())).thenReturn(Option.of(795));

        Basket basket = new NotEmptyBasket(795);

        // SMELL Duplicates logic in PointOfSale.runApplication(): stream lines, handle each line, consume the result
        Assertions.assertEquals(
                List.of("CAD 7.95", "Total: CAD 7.95"),
                List.of("12345", "total").stream().map(line -> PointOfSale.handleLine(line, basket, new LegacyCatalogAdapter(legacyCatalog))).collect(Collectors.toList()));
    }

    @Test
    void aDifferentItem() {
        LegacyCatalog legacyCatalog = Mockito.mock(LegacyCatalog.class);
        Mockito.when(legacyCatalog.findPrice(Mockito.any())).thenReturn(Option.of(995));

        Basket basket = new NotEmptyBasket(995);

        // SMELL Duplicates logic in PointOfSale.runApplication(): stream lines, handle each line, consume the result
        Assertions.assertEquals(
                List.of("CAD 9.95", "Total: CAD 9.95"),
                List.of("12345", "total").stream().map(line -> PointOfSale.handleLine(line, basket, new LegacyCatalogAdapter(legacyCatalog))).collect(Collectors.toList()));
    }
}
