package ca.jbrains.pos.test;

import ca.jbrains.pos.Barcode;
import ca.jbrains.pos.PointOfSale;
import ca.jbrains.pos.domain.Basket;
import ca.jbrains.pos.domain.Catalog;
import ca.jbrains.pos.domain.PurchaseProvider;
import io.vavr.control.Either;
import io.vavr.control.Option;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class TestSellMultipleItems {
    private boolean startPurchaseInvoked = false;

    @Disabled("wip")
    @Test
    void differentCustomersShouldHaveSeparateBaskets() {
        // REFACTOR Maybe use Mockito to stub?
        Catalog catalog = new Catalog() {
            @Override
            public Either<Barcode, Integer> findPrice(Barcode barcode) {
                return new Barcode("12345").equals(barcode)
                        ? Either.right(100)
                        : Either.right(150);
            }
        };

        RecordingBasket basket = new RecordingBasket();
        PurchaseProvider purchaseProvider = new PurchaseProvider() {
            @Override
            public void startPurchase() {
            }

            public int getTotal() {
                return basket.getTotal();
            }
        };
      PointOfSale.handleBarcode(new Barcode("12345"), catalog, basket, purchaseProvider);
        PointOfSale.handleTotal(purchaseProvider);

        RecordingBasket secondShopperBasket = new RecordingBasket();
      PointOfSale.handleBarcode(new Barcode("67890"), catalog, basket, purchaseProvider);
      purchaseProvider = new PurchaseProvider() {
            @Override
            public void startPurchase() {
            }

            public int getTotal() {
                return secondShopperBasket.getTotal();
            }
        };
        PointOfSale.handleTotal(purchaseProvider);

        Assertions.assertEquals(Option.of(100), basket.recentPrice);
        Assertions.assertEquals(Option.of(150), secondShopperBasket.recentPrice);
    }

    @Test
    void newPurchaseUsesNewBasket() {
        Basket dummyBasket = new Basket() {
            @Override
            public void add(int price) {
                // Intentionally do nothing
            }

            @Override
            public int getTotal() {
                // irrelevant value
                return -1;
            }
        };
        PointOfSale.handleTotal(new PurchaseProvider() {
            @Override
            public void startPurchase() {
                TestSellMultipleItems.this.startPurchaseInvoked = true;
            }

            @Override
            public int getTotal() {
                return dummyBasket.getTotal();
            }
        });

        Assertions.assertEquals(true, startPurchaseInvoked);
    }
}
