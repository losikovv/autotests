package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.autotests.models.RetailerData;

public class ShippingAddress extends TestBase {


    @Test(
            description = "Проверяем что по умолчанию на витрине ритейлера не выбран адрес",
            groups = {"acceptance","regression"},
            priority = 201
    )
    public void emptyShippingAddressByDefault() throws Exception {
        app.getNavigationHelper().getRetailerPage(new RetailerData("metro"));

        // Assert shipping address isn't set
        Assert.assertTrue(app.getShoppingHelper().isShippingAddressEmpty(),
                "Shipping address is not empty on retailer page while unauthorized\n");
    }


    @Test(
            description = "Проверяем что на лендинге по адресу вне зоны доставки нет доступных магазинов",
            groups = {"acceptance","regression"},
            priority = 202
    )
    public void setAddressWithNoAvailableShopsOnLanding() throws Exception {
        app.getNavigationHelper().getLandingPage();
        app.getShoppingHelper().setShippingAddress("Москва, ул Лосиноостровская, д 1"); // TODO параметризовать и брать адрес из справочника

        // Assert shops list is open
        Assert.assertTrue(app.getShoppingHelper().isShopsListOpen(),
                "Shops list isn't open\n");

        // Assert shops list is empty
        Assert.assertTrue(app.getShoppingHelper().isShopsListEmpty(),
                "Shipping address is out of the shipping zone, but the shops list isn't empty\n");
    }


    @Test(
            enabled = false,
            description = "Проверяем открытие и закрытие списка магазинов на витрине",
            groups = {"acceptance","regression"},
            priority = 203
    )
    public void openAndCloseShopsList() throws Exception {
        app.getNavigationHelper().getRetailerPage(new RetailerData("metro"));
        app.getShoppingHelper().setShippingAddress("Москва, ул Люсиновская, д 55");
        app.getShoppingHelper().openShopsList();

        // Assert shops list is open
        Assert.assertTrue(app.getShoppingHelper().isShopsListOpen(),
                "Can't open shops list\n");

        app.getShoppingHelper().closeShopsList();

        // Assert shops list isn't open
        Assert.assertFalse(app.getShoppingHelper().isShopsListOpen(),
                "Can't close shops list\n");
    }


    @Test(
            description = "Проверяем что на лендинге по адресу в зоне доставки есть доступные магазины",
            groups = {"acceptance","regression"},
            priority = 204
    )
    public void setAddressWithAvailableShopsOnLanding() throws Exception {
        app.getNavigationHelper().getLandingPage();
        app.getShoppingHelper().setShippingAddress("Москва, ул Долгоруковская, д 2"); // TODO параметризовать и брать адрес из справочника

        // Assert shops list is open
        Assert.assertTrue(app.getShoppingHelper().isShopsListOpen(),
                "Shops list isn't open\n");

        // Assert shops list is empty
        Assert.assertFalse(app.getShoppingHelper().isShopsListEmpty(),
                "Shipping address is in the shipping zone, but the shops list is empty\n");

        // Assert shops available in the list
        Assert.assertTrue(app.getShoppingHelper().isAnyShopsAvailable(),
                "Shipping address is in the shipping zone, but there is no shops available in the list\n");
    }


    




    @Test(enabled = false, priority = 5, description = "Проверяем что на витрине по адресу вне зоны доставки нет доступных магазинов")
    public void setAddressWithNoAvailableShops() throws Exception {
        app.getNavigationHelper().getRetailerPage(new RetailerData("metro"));
        app.getShoppingHelper().changeShippingAddress("Москва, ул Лосиноостровская, д 1");
        app.getShoppingHelper().openShopsList();

        Assert.assertTrue(app.getShoppingHelper().isShopsListOpen(),
                "Shops list isn't open");

        Assert.assertTrue(app.getShoppingHelper().isShopsListEmpty(),
                "Shipping address is out of the shipping zone, but the shops list isn't empty\n");
    }

    @Test(enabled = false, priority = 6, description = "Проверяем что на витрине по адресу в зоне доставки есть доступные магазины")
    public void setAddressWithAvailableShops() throws Exception {
        app.getNavigationHelper().getRetailerPage(new RetailerData("metro"));
        app.getShoppingHelper().changeShippingAddress("Москва, ул Долгоруковская, д 2");
        app.getShoppingHelper().openShopsList();

        Assert.assertTrue(app.getShoppingHelper().isShopsListOpen(),
                "Shops list isn't open");

        Assert.assertFalse(app.getShoppingHelper().isShopsListEmpty(),
                "Shipping address is in the shipping zone, but the shops list is empty\n");

        Assert.assertTrue(app.getShoppingHelper().isAnyShopsAvailable(),
                "Shipping address is in the shipping zone, but there is no shops available in the list\n");
    }

    @Test(enabled = false)
    public void setAddressAndShopOnLandingPage() throws Exception {
        app.getNavigationHelper().getLandingPage();
        app.getShoppingHelper().setShippingAddress("Москва, ул Долгоруковская, д 2");
        app.getShoppingHelper().selectShop(1);

        Assert.assertFalse(app.getShoppingHelper().isShippingAddressEmpty(),
                "Shipping address is empty after it was set on landing\n");

        Assert.assertFalse(app.getShoppingHelper().isShippingAddressEmpty(),
                "Shipping address wasn't set\n");
    }

    //TODO @Test setAddressAndShop - на витрине

    @Test(enabled = false)
    public void changeShippingAddressOnRetailerPage() throws Exception {
        app.getNavigationHelper().getRetailerPage(new RetailerData("metro"));
        app.getShoppingHelper().changeShippingAddress("Москва, ул Тверская, д 1");
        app.getShoppingHelper().selectShop(1);

        Assert.assertFalse(app.getShoppingHelper().isShippingAddressEmpty(),
                "Shipping address is empty after it was set on landing\n");

        Assert.assertFalse(app.getShoppingHelper().isShippingAddressEmpty(),
                "Shipping address wasn't set\n");
    }

    // TODO добавить тесты на проверку совпадения выбранного адреса введенному (лендинг + ретайлер)

/*
    @Test(priority = 2)
    public void selectShop() throws Exception {

    }

    @Test(priority = 3)
    public void addItemToCart() throws Exception {
        app.getNavigationHelper().getPage("metro?sid=12");
        app.getShoppingHelper().addFirstLineItemOnPageToCart();
        app.getShoppingHelper().openCart();
        // работает только если корзина была пуста, нужно переделать
        Assert.assertFalse(app.getShoppingHelper().isCartEmpty(), "Item hasn't been added to cart");
    }

    */

}
