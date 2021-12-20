package ca.jbrains.pos.domain;

public class Price {
    private final int moneyInCents;

    public Price(int moneyInCents) {
        this.moneyInCents = moneyInCents;
    }

    public int getAmount() {
        return moneyInCents;
    }
}
