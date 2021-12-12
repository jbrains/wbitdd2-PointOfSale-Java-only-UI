This code base is an exercise in evolutionary design/TDD with the following goals:

- Focus on the border between the Happy Zone and the DMZ, notably isolating parts of the UI from the UI technology.
- Allow participants in the live sessions to focus on design decisions, rather than worry about the details of how to write the code.
- Practise programming to abstractions, such as using collaboration and contract tests.
    - Practise documenting and clarifying the contracts of the abstractions we use.
- Explore intermediate refactoring patterns.

# Overview

We use the Point of Sale System as a source of features. We build the Shell instead without building the Core. The boundary between the Shell and the Core is evolving over time as our collective understanding evolves. As of this writing, the Core currently represents most of the Model. I expect these boundaries to change over time.

# Example: Sell One Item

For the Sell One Item feature, we did not build the Core that determines the matching price for a barcode. Instead, we modeled this as an abstraction using an interface.

```
public interface SaleController {
  String getPrice(String barcode);
}
```

Since we are writing code in Java, this interface is equivalent to a lambda expression of the type `String -> String`. Also, we since we are writing code in Java, the type of this lambda expression is really `nullable String -> Either<RuntimeException, nullable String>`.

When we built the Shell of this feature, we chose the following contract of `getPrice()`:

- return `null` -> no matching price for the barcode
- return anything else -> formatted text representing the price of the product whose barcode was requested
- throw exception -> unrecoverable error
- no restrictions on input, so `null` barcode -> undefined behavior
- no validation of barcode, so no distinction between "invalid barcode" and "price not found"

