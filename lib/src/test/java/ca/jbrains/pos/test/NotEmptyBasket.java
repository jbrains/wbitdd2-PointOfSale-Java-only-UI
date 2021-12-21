package ca.jbrains.pos.test;

import ca.jbrains.pos.domain.Basket;

class NotEmptyBasket implements Basket {
    private final int price;

    public NotEmptyBasket(int price) {
        this.price = price;
    }

    @Override
    public void add(int price) {
    }

    @Override
    public int getTotal() {
        return price;
    }
}
