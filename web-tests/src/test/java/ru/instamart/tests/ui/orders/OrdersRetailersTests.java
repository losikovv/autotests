package ru.instamart.tests.ui.orders;

import ru.instamart.api.common.RestAddresses;
import ru.instamart.core.testdata.UserManager;
import ru.instamart.ui.checkpoints.BaseUICheckpoints;
import ru.instamart.ui.common.lib.Addresses;
import ru.instamart.ui.common.lib.Pages;
import ru.instamart.ui.modules.Shop;
import ru.instamart.ui.modules.User;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.tests.ui.TestBase;
import ru.instamart.ui.modules.shop.ShippingAddressModal;

import static ru.instamart.ui.common.lib.Retailers.*;

public class OrdersRetailersTests extends TestBase {
    BaseUICheckpoints baseChecks = new BaseUICheckpoints();
    @BeforeClass(alwaysRun = true,
            description = "Подготавливаем тестовое окружение к прогону тестов")
    public void setup() {
        kraken.get().baseUrl();
        User.Do.loginAs(UserManager.getDefaultAdmin());
        ShippingAddressModal.open();
        ShippingAddressModal.fill(Addresses.Moscow.defaultAddress());
        ShippingAddressModal.submit();
    }

    @AfterMethod(alwaysRun = true,
            description ="Очищаем тестовое окружение после теста")
    public void afterTest(ITestResult result) {
        kraken.apiV2().cancelCurrentOrder();
    }

    @Test(description = "Тестовый заказ в Метро Москва",

            groups = {
                    "metro-acceptance","metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successOrderInMetro(){
        baseChecks.checkPageIsAvailable(Pages.Retailers.metro());

        kraken.apiV2().fillCart(UserManager.getDefaultAdmin(), RestAddresses.Moscow.defaultAddress(), metro);

        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderPlaced(),
                    failMessage("Не удалось оформить заказ в Метро Москва"));
    }

    @Test(description = "Тестовый заказ в Ашан Москва",

            groups = {
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successOrderInAuchan(){
        runTestOnlyOnTenant("sbermarket");
        baseChecks.checkPageIsAvailable(Pages.Retailers.auchan());

        kraken.apiV2().fillCart(UserManager.getDefaultAdmin(), RestAddresses.Moscow.defaultAddress(), auchan);

        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderPlaced(),
                    failMessage("Не удалось оформить заказ в Ашан Москва"));
    }

    @Test(description = "Тестовый заказ в Азбука Вкуса Москва",

            groups = {
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successOrderInAzbukaVkusa(){
        runTestOnlyOnTenant("sbermarket");
        baseChecks.checkPageIsAvailable(Pages.Retailers.azbuka());

        kraken.apiV2().fillCart(UserManager.getDefaultAdmin(), RestAddresses.Moscow.defaultAddress(), azbuka);

        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderPlaced(),
                    failMessage("Не удалось оформить заказ в Азбука Вкуса Москва"));
    }

    @Test(description = "Тестовый заказ в Вкусвилл Москва",

            groups = {
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successOrderInVkusvill(){
        runTestOnlyOnTenant("sbermarket");
        baseChecks.checkRetailerIsAvailable(vkusvill);
        kraken.apiV2().fillCart(UserManager.getDefaultAdmin(), RestAddresses.Moscow.defaultAddress(), vkusvill);

        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderPlaced(),
                failMessage("Не удалось оформить заказ в Вкусвилл Москва"));
    }

    @Test(description = "Тестовый заказ в Лента Москва",

            groups = {
                    "sbermarket-acceptance","sbermarket-regression"
            }
    ) public void successOrderInLenta(){
        runTestOnlyOnTenant("sbermarket");
        baseChecks.checkRetailerIsAvailable(lenta);
        kraken.apiV2().fillCart(UserManager.getDefaultAdmin(), RestAddresses.Moscow.defaultAddress(), lenta);

        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(
                kraken.detect().isOrderPlaced(),
                    failMessage("Не удалось оформить заказ в Лента Москва"));
    }


}
