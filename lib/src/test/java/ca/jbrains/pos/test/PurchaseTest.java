package ca.jbrains.pos.test;

import ca.jbrains.pos.PointOfSale;
import ca.jbrains.pos.domain.Basket;
import ca.jbrains.pos.domain.Catalog;
import io.vavr.control.Option;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

public class PurchaseTest {
    @Test
    void oneItem() {
        Catalog catalog = ignored -> Option.of(795);
        Basket basket = new Basket() {
            @Override
            public void add(int price) {
            }

            @Override
            public int getTotal() {
                return 795;
            }
        };

        Assertions.assertEquals(
                List.of("CAD 7.95", "Total: CAD 7.95"),
                List.of("12345", "total").stream().map(line -> PointOfSale.handleLine(line, basket, catalog)).collect(Collectors.toList()));
    }

    @Test
    void aDifferentItem() {
        Catalog catalog = ignored -> Option.of(995);
        Basket basket = new Basket() {
            @Override
            public void add(int price) {
            }

            @Override
            public int getTotal() {
                return 995;
            }
        };

        Assertions.assertEquals(
                List.of("CAD 9.95", "Total: CAD 9.95"),
                List.of("12345", "total").stream().map(line -> PointOfSale.handleLine(line, basket, catalog)).collect(Collectors.toList()));
    }
}
