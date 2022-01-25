package ca.jbrains.pos.test;

import ca.jbrains.pos.Barcode;
import ca.jbrains.pos.PointOfSale;
import ca.jbrains.pos.domain.Catalog;
import ca.jbrains.pos.domain.PurchaseProvider;
import io.vavr.control.Either;
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

        PurchaseProvider purchaseProvider = new PurchaseProvider() {
            @Override
            public void startPurchase() {
            }

            public int getTotal() {
                return -1;
            }

            @Override
            public void addPriceOfScannedItem(int price) {

            }
        };
      PointOfSale.handleBarcode(new Barcode("12345"), catalog, purchaseProvider);
        PointOfSale.handleTotal(purchaseProvider);

      PointOfSale.handleBarcode(new Barcode("67890"), catalog, purchaseProvider);
      purchaseProvider = new PurchaseProvider() {
            @Override
            public void startPurchase() {
            }

            public int getTotal() {
                return -1;
            }

          @Override
          public void addPriceOfScannedItem(int price) {

          }
      };
        PointOfSale.handleTotal(purchaseProvider);

        Assertions.fail("PurchaseProvider needs to verify the items that were added");
    }

    @Test
    void newPurchaseUsesNewBasket() {
        PointOfSale.handleTotal(new PurchaseProvider() {
            @Override
            public void startPurchase() {
                TestSellMultipleItems.this.startPurchaseInvoked = true;
            }

            @Override
            public int getTotal() {
                return -1;
            }

            @Override
            public void addPriceOfScannedItem(int price) {

            }
        });

        Assertions.assertEquals(true, startPurchaseInvoked);
    }
}
