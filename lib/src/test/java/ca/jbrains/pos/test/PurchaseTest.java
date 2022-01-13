package ca.jbrains.pos.test;

import ca.jbrains.pos.Catalog;
import ca.jbrains.pos.PointOfSale;
import ca.jbrains.pos.domain.Basket;
import io.vavr.control.Either;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.stream.Collectors;

public class PurchaseTest {
    @Test
    void oneItem() {
        Catalog catalog = Mockito.mock(Catalog.class);
        Mockito.when(catalog.findPrice(Mockito.any())).thenReturn(Either.right(795));

        Basket basket = new NotEmptyBasket(795);

        // SMELL Duplicates logic in PointOfSale.runApplication(): stream lines, handle each line, consume the result
        Assertions.assertEquals(
                List.of("CAD 7.95", "Total: CAD 7.95"),
                List.of("12345", "total").stream().map(line -> PointOfSale.handleLine(line, basket, catalog)).collect(Collectors.toList()));
    }

    @Test
    void aDifferentItem() {
        Catalog catalog = Mockito.mock(Catalog.class);
        Mockito.when(catalog.findPrice(Mockito.any())).thenReturn(Either.right(995));

        Basket basket = new NotEmptyBasket(995);

        // SMELL Duplicates logic in PointOfSale.runApplication(): stream lines, handle each line, consume the result
        Assertions.assertEquals(
                List.of("CAD 9.95", "Total: CAD 9.95"),
                List.of("12345", "total").stream().map(line -> PointOfSale.handleLine(line, basket, catalog)).collect(Collectors.toList()));
    }
}
