package ca.jbrains.pos.test;

import ca.jbrains.pos.domain.Basket;
import io.vavr.control.Option;

public class RecordingBasket implements Basket {
    public Option<Integer> recentPrice;

    @Override
    public void add(int price) {
        recentPrice = Option.some(price);
    }

    @Override
    public int getTotal() {
        return 0;
    }
}
