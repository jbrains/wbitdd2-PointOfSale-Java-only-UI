package ca.jbrains.pos.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestSellMultipleItems {
    @Test
    void differentCustomersShouldHaveSeparateBaskets() {
        // scan some items, but not item ::magic item that second shopper will buy::
        // send total event (checkout) -> (total 1, basket 1)
        // scan item ::magic item that second shopper will buy::
        // check that only basket 2 contains item ::magic item that second shopper will buy::
        Assertions.fail("BOOKMARK");
    }
}
