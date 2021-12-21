package ca.jbrains.pos.domain;

public interface Basket {
    void add(int price);

    int getTotal();
}
