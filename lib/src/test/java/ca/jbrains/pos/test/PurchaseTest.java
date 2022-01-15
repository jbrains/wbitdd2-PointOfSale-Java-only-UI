package ca.jbrains.pos.test;

import ca.jbrains.pos.Catalog;
import ca.jbrains.pos.PointOfSale;
import ca.jbrains.pos.domain.Basket;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.util.List;
import java.util.stream.Collectors;

public class PurchaseTest {
    @RegisterExtension
    final JUnit5Mockery context = new JUnit5Mockery();
    private CatalogFixtureSetup catalogFixtureSetup;
    private Catalog catalog;

    @BeforeEach
    void setUp() {
        catalogFixtureSetup = new CatalogFixtureSetup();
        catalog = context.mock(Catalog.class);
    }

    @Test
    void oneItem() {
        Basket basket = new NotEmptyBasket(795);
        catalogFixtureSetup.catalogWithPrice(context, 795, catalog);

        // SMELL Duplicates logic in PointOfSale.runApplication(): stream lines, handle each line, consume the result
        Assertions.assertEquals(
                List.of("CAD 7.95", "Total: CAD 7.95"),
                List.of("12345", "total").stream()
                        .map(line -> PointOfSale.handleLine(line,
                                basket,
                                catalog))
                        .collect(Collectors.toList()));
    }

    @Test
    void aDifferentItem() {
        Basket basket = new NotEmptyBasket(995);
        catalogFixtureSetup.catalogWithPrice(context,995, catalog);
        // SMELL Duplicates logic in PointOfSale.runApplication(): stream lines, handle each line, consume the result
        Assertions.assertEquals(
                List.of("CAD 9.95", "Total: CAD 9.95"),
                List.of("12345", "total").stream()
                        .map(line -> PointOfSale.handleLine(line,
                                basket,
                                catalog))
                        .collect(Collectors.toList()));
    }
}
