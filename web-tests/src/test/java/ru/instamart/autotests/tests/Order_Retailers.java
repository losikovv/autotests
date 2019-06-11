package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.instamart.autotests.application.Addresses;
import ru.instamart.autotests.application.Environments;
import ru.instamart.autotests.appmanager.ShopHelper;

import static ru.instamart.autotests.application.Config.testRetailerOrders;


// Тесты заказов во всех ритейлерах присутсвия


public class Order_Retailers extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() throws Exception {
        kraken.get().baseUrl();
        kraken.perform().loginAs(kraken.session.user);
        kraken.shipAddress().change(Addresses.Moscow.testAddress());
    }

    @Test(enabled = testRetailerOrders,
            description = "Тестовый заказ в Метро Москва",
            groups = {"acceptance","regression"},
            priority = 941
    )
    public void successOrderInMetro(){
        kraken.get().page("metro");
        kraken.drop().cart();

        kraken.shopping().collectItems();
        ShopHelper.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не удалось оформить заказ в Метро Москва\n");
    }

    @Test(enabled = false,
            description = "Тестовый заказ во Вкусвилл Москва с применением бонусной программы",
            groups = {"acceptance","regression"},
            priority = 942
    )
    public void successOrderInVkusvill(){
        skipOn(Environments.metro_production());
        kraken.get().page("vkusvill");
        kraken.drop().cart();

        kraken.shopping().collectItems();
        // TODO перенести проверку добавления карты Вкусвилл в тесты чекаута
        //Assert.assertTrue(kraken.detect().isRetailerLoyaltyAvailable(),"Не доступна бонусная программа Вкусвилл \n");
        //kraken.checkout().addLoyalty(LoyaltyPrograms.vkusvill());
        //Assert.assertTrue(kraken.detect().isLoyaltyAdded(LoyaltyPrograms.vkusvill()),"Не применяется бонусная программа Вкусвилл\n");
        ShopHelper.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не удалось оформить заказ во Вкусвилл Москва\n");
    }

    @Test(enabled = testRetailerOrders,
            description = "Тестовый заказ в Ашан Москва",
            groups = {"acceptance","regression"},
            priority = 943
    )
    public void successOrderInAuchan(){
        skipOn(Environments.metro_production());
        kraken.get().page("auchan");
        kraken.drop().cart();

        kraken.shopping().collectItems();
        ShopHelper.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не удалось оформить заказ в Ашан Москва\n");
    }

    @Test(enabled = false,
            description = "Тестовый заказ в Ленту Москва",
            groups = {"acceptance","regression"},
            priority = 944
    )
    public void successOrderInLenta(){
        skipOn(Environments.metro_production());
        kraken.get().page("lenta");
        kraken.drop().cart();

        kraken.shopping().collectItems();
        ShopHelper.Cart.proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не удалось оформить заказ в Ленту Москва\n");
    }

    @AfterMethod(alwaysRun = true)
    public void postconditions() {
        kraken.perform().cancelLastOrder();
    }
}
