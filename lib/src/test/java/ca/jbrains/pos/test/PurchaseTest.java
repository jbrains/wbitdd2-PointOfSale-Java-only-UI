package ca.jbrains.pos.test;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PurchaseTest {
    @Disabled("WIP: Deciding how to write the action for this test")
    @Test
    void oneItem() {
        List<String> commands = List.of("12345", "total");

        List<String> responseMessages = List.of("CAD 7.95", "Total: CAD 7.95");
    }
}
