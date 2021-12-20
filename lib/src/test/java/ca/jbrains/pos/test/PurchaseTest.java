package ca.jbrains.pos.test;

import ca.jbrains.pos.PointOfSale;
import ca.jbrains.pos.domain.Basket;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

public class PurchaseTest {
    @Test
    void oneItem() {
        Basket basket = () -> 795;

        Assertions.assertEquals(
                List.of("CAD 7.95", "Total: CAD 7.95"),
                List.of("12345", "total").stream().map(line -> PointOfSale.handleLine(line, basket)).collect(Collectors.toList()));
    }
}
