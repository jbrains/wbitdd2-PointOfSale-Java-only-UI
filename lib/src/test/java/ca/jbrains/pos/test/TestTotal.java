package ca.jbrains.pos.test;

import ca.jbrains.pos.Barcode;
import ca.jbrains.pos.PointOfSale;
import ca.jbrains.pos.domain.Basket;
import io.vavr.control.Option;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestTotal {
    @Test
    void noItems() {
        Assertions.assertEquals("Total: CAD 0.00", PointOfSale.handleTotal(new Basket() {
            @Override
            public void add(int price) {
            }

            @Override
            public int getTotal() {
                return 0;
            }
        }));
    }

    @Test
    void oneItem() {
        Assertions.assertEquals("Total: CAD 1.02", PointOfSale.handleTotal(new Basket() {
            @Override
            public void add(int price) {
            }

            @Override
            public int getTotal() {
                return 102;
            }
        }));
    }
}
