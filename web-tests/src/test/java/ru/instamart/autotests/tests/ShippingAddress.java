package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;



    // Тесты адреса доставки



public class ShippingAddress extends TestBase {


    @Test(
            description = "Проверяем что на лендинге не выбран адрес",
            groups = {"acceptance","regression"},
            priority = 200
    )
    public void emptyShippingAddressOnLanding() throws Exception {
        app.getNavigationHelper().getLandingPage();

        // Assert shipping address isn't set
        Assert.assertTrue(app.getShoppingHelper().isShippingAddressEmpty(),
                "Shipping address is not empty on the landing page\n");
    }


    @Test(
            description = "Проверяем что по дефолту на витрине ритейлера не выбран адрес",
            groups = {"acceptance","regression"},
            priority = 201
    )
    public void emptyShippingAddressOnRetailerByDefault() throws Exception {
        app.getNavigationHelper().getRetailerPage("metro");

        // Assert shipping address isn't set
        Assert.assertTrue(app.getShoppingHelper().isShippingAddressEmpty(),
                "Shipping address is not empty on the retailer page by default\n");
    }


    @Test(
            description = "Тест дефолтного списка магазов при отсутствии адреса доставки",
            groups = {"acceptance","regression"},
            priority = 202
    )
    public void checkDefaultShoplist() throws Exception {
        app.getNavigationHelper().getRetailerPage("metro");
        app.getShoppingHelper().openShopsList();

        // Assert shoplist is open
        Assert.assertTrue(app.getShoppingHelper().isShopsListOpen(),
                "Can't open shoplist\n");

        // Assert shoplist isn't empty
        Assert.assertFalse(app.getShoppingHelper().isShopsListEmpty(),
                "Shoplist is not supposed to be empty by default\n");

        // Assert there is at least one shop in the shoplist
        Assert.assertTrue(app.getShoppingHelper().isAnyShopsAvailable(),
                "There is no shops available in the shoplist\n");

        app.getShoppingHelper().closeShopsList();

        // Assert shoplist isn't open
        Assert.assertFalse(app.getShoppingHelper().isShopsListOpen(),
                "Can't close shoplist\n");

    }


    @Test(
            description = "Тест выбора адреса на лендинге",
            groups = {"acceptance","regression"},
            priority = 203
    )
    public void setShippingAddressOnLandingPage() throws Exception {
        final String address = "Москва, ул Мусы Джалиля, д 19 к 1";
        app.getNavigationHelper().getLandingPage();
        app.getShoppingHelper().setShippingAddress(address);
        app.getShoppingHelper().selectShop(1);

        // Assert shipping address is set
        Assert.assertTrue(app.getShoppingHelper().isShippingAddressSet(),
                "Can't approve the shipping address was set correctly, check manually\n");

        //
        Assert.assertTrue(app.getShoppingHelper().currentShippingAddress().equals(address),
                "Current shipping address is not the same that was entered during the setting procedure\n");
    }


    @Test(
            description = "Тест изменения адреса доставки на витрине ритейлера",
            groups = {"acceptance","regression"},
            priority = 204
    )
    public void changeShippingAddress() throws Exception {
        app.getNavigationHelper().getRetailerPage("metro");

        if(app.getShoppingHelper().isShippingAddressEmpty()){
            app.getShoppingHelper().setShippingAddress("Москва, ул Мусы Джалиля, д 19 к 1");
        }

        String initialAddress = app.getShoppingHelper().currentShippingAddress();
        final String newAddress1 = "Москва, ул Пироговская М., д 88";
        final String newAddress2 = "Москва, Ленинградское шоссе, д 50";

        // проверяем на совпадение с текущим адресом доставки и меняем адрес
        if(initialAddress.equals(newAddress1)){
            app.getShoppingHelper().changeShippingAddress(newAddress2);
        } else {
            app.getShoppingHelper().changeShippingAddress(newAddress1);
        }

        // Assert shipping address is set
        Assert.assertTrue(app.getShoppingHelper().isShippingAddressSet(),
                "Can't approve the shipping address was set correctly, check manually\n");

        // Assert shipping address was changed
        Assert.assertFalse(app.getShoppingHelper().currentShippingAddress().equals(initialAddress),
                "Shipping address was changed\n");
    }


    @Test( enabled = false,
            description = "Проверяем что на лендинге по адресу вне зоны доставки нет доступных магазинов",
            groups = {"acceptance","regression"},
            priority = 205
    )
    public void setAddressWithNoAvailableShops() throws Exception {
        app.getNavigationHelper().getRetailerPage("metro");
        app.getShoppingHelper().changeShippingAddress("Москва, ул Лосиноостровская, д 1");
        app.getShoppingHelper().openShopsList();

        // Assert shops list is open
        Assert.assertTrue(app.getShoppingHelper().isShopsListOpen(),
                "Shops list isn't open\n");

        // Assert shops list is empty
        Assert.assertTrue(app.getShoppingHelper().isShopsListEmpty(),
                "Shipping address is out of the shipping zone, but the shops list isn't empty\n");
    }


    @Test(
            description = "Проверяем что после авторизации подтягивается адрес доствки",
            groups = {"acceptance","regression"},
            priority = 206
    )
    public void sucessShippingAddressFetchAfterAuthorisation() throws Exception {
        app.getNavigationHelper().getLandingPage();
        app.getSessionHelper().doLoginAs("admin");

        // Assert shipping address is set after authorisation
        Assert.assertTrue(app.getShoppingHelper().isShippingAddressSet(),
                "Shipping address is not fetched after authorisation, check manually\n");

    }

    // TODO тест на изменение выбранного магазина

}
