package ca.jbrains.pos.test;

import ca.jbrains.pos.PointOfSale;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HandleTextCommandTest {
    @Test
    void emptyCommand() {
        Assertions.assertEquals(
                "Error: empty command",
                PointOfSale.handleCommand("")
        );
    }
}
