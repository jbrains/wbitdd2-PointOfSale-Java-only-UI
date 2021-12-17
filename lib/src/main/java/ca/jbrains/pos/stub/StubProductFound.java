package ca.jbrains.pos.stub;

import ca.jbrains.pos.domain.Price;
import ca.jbrains.pos.domain.ProductFound;

public class StubProductFound implements ProductFound {
    @Override
    public Price getPrice() {
        return new Price(100);
    }
}
