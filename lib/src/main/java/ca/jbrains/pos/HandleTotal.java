package ca.jbrains.pos;

import ca.jbrains.pos.domain.PurchaseAccumulator;

public record HandleTotal(PurchaseAccumulator purchaseAccumulator,
                          FormatMonetaryAmount formatMonetaryAmount) implements Controller<Void> {

    public String handleTotal() {
        return purchaseAccumulator().completePurchase().fold(
                () -> "There is no purchase in progress; please scan an item.",
                (purchase) -> new FormatTotal(formatMonetaryAmount()).formatTotal(purchase.total()));
    }

    @Override
    public String handleRequest(Void unused) {
        return handleTotal();
    }
}
