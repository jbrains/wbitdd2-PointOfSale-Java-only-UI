package ca.jbrains.pos.test;

import ca.jbrains.pos.domain.PurchaseProvider;
import io.vavr.control.Option;

public class RecordingPurchaseProvider implements PurchaseProvider {
    public Option<Integer> priceOfMostRecentItemAdded = Option.none();

    @Override
    public void startPurchase() {
    }

    @Override
    public int getTotal() {
        return 0;
    }

    @Override
    public void addItem(int price) {
        this.priceOfMostRecentItemAdded = Option.some(price);
    }
}
