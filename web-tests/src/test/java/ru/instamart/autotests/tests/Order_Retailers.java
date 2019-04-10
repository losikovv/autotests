package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.instamart.autotests.application.Addresses;
import ru.instamart.autotests.application.Environments;


// Тесты заказов во всех ритейлерах присутсвия


public class Order_Retailers extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() throws Exception {
        kraken.get().baseUrl();
        kraken.perform().loginAs(kraken.session.user);
        kraken.shipAddress().change(Addresses.Moscow.testAddress());
    }


    @Test(
            description = "Тестовый заказ в Метро Москва",
            groups = {"acceptance","regression"},
            priority = 911
    )
    public void successOrderInMetro(){
        kraken.get().page("metro");
        kraken.drop().cart();

        kraken.shopping().collectItems();
        kraken.shopping().proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не удалось оформить заказ в Метро Москва\n");
    }


    @Test(
            description = "Тестовый заказ во Вкусвилл Москва с применением бонусной программы",
            groups = {"acceptance","regression"},
            priority = 912
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
        kraken.shopping().proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не удалось оформить заказ во Вкусвилл Москва\n");
    }


    @Test(
            description = "Тестовый заказ в Ашан Москва",
            groups = {"acceptance","regression"},
            priority = 913
    )
    public void successOrderInAuchan(){
        skipOn(Environments.metro_production());
        kraken.get().page("auchan");
        kraken.drop().cart();

        kraken.shopping().collectItems();
        kraken.shopping().proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не удалось оформить заказ в Ашан Москва\n");
    }


    @Test(
            description = "Тестовый заказ в Ленту Москва",
            groups = {"acceptance","regression"},
            priority = 914
    )
    public void successOrderInLenta(){
        skipOn(Environments.metro_production());
        kraken.get().page("lenta");
        kraken.drop().cart();

        kraken.shopping().collectItems();
        kraken.shopping().proceedToCheckout();
        kraken.checkout().complete();

        Assert.assertTrue(kraken.detect().isOrderActive(),
                "Не удалось оформить заказ в Ленту Москва\n");
    }

    @AfterMethod(alwaysRun = true)
    public void postconditions() {
        kraken.perform().cancelLastOrder();
    }
}
