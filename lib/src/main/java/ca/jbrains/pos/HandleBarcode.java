package ca.jbrains.pos;

import ca.jbrains.pos.domain.Catalog;
import ca.jbrains.pos.domain.PurchaseAccumulator;

// SMELL jbrains: "bit strange but we leave it for now"
public class HandleBarcode implements BarcodeController {
    private final Catalog catalog;
    private final HandleProductFound handleProductFound;
    private final FormatMonetaryAmount formatMonetaryAmount;
    private final HandleProductNotFound handleProductNotFound;

    public HandleBarcode(PurchaseAccumulator purchaseAccumulator, Catalog catalog, FormatMonetaryAmount formatMonetaryAmount) {
        this.catalog = catalog;
        // SMELL accept them as parameter instead?
        this.handleProductFound = new HandleProductFound(purchaseAccumulator);
        this.formatMonetaryAmount = formatMonetaryAmount;
        this.handleProductNotFound = new HandleProductNotFound();
    }

    @Override
    public String handleBarcode(Barcode barcode) {
        return this.catalog.findPrice(barcode).fold(
                handleProductNotFound::handleProductNotFound,
                price -> handleProductFound.handleProductFound(price, this.formatMonetaryAmount)
        );
    }
}
