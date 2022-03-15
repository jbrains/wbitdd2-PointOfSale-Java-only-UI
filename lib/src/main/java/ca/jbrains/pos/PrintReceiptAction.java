package ca.jbrains.pos;

public interface PrintReceiptAction extends Controller<Void> {
    String printReceipt();

    @Override
    default String handleRequest(Void ignored) {
        return printReceipt();
    }
}
