package ca.jbrains.pos;

public class HandleProductNotFound {
    public String handleProductNotFound(Barcode barcode) {
        return String.format("Product not found: %s", barcode.text());
    }
}
