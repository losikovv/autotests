package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;



    // Тесты адреса доставки



public class ShippingAddressOLD extends TestBase {

 /*




    @Test(enabled = false,
            description = "Тест дефолтного списка магазов при отсутствии адреса доставки",
            groups = {"acceptance","regression"},
            priority = 202
    )
    public void checkDefaultShoplist() throws Exception {
        kraken.get().retailerPage("metro");
        kraken.getShoppingHelper().openShopsList();

        // Assert shoplist is open
        Assert.assertTrue(kraken.getShoppingHelper().isShopsListOpen(),
                "Can't open shoplist\n");

        // Assert shoplist isn't empty
        Assert.assertFalse(kraken.getShoppingHelper().isShopsListEmpty(),
                "Shoplist is not supposed to be empty by default\n");

        // Assert there is at least one shop in the shoplist
        Assert.assertTrue(kraken.getShoppingHelper().isAnyShopsAvailable(),
                "There is no shops available in the shoplist\n");

        kraken.getShoppingHelper().closeShopSelector();

        // Assert shoplist isn't open
        Assert.assertFalse(kraken.getShoppingHelper().isShopsListOpen(),
                "Can't close shoplist\n");

    }


    @Test(enabled = false,
            description = "Тест выбора адреса на лендинге",
            groups = {"acceptance","regression"},
            priority = 203
    )
    public void setShippingAddressOnLandingPage() throws Exception {
        final String address = "Москва, ул Мусы Джалиля, д 19 к 1"; // TODO брать из Addresses
        kraken.get().getLandingPage();
        kraken.getShoppingHelper().setShippingAddress(address);
        kraken.getShoppingHelper().selectShop(1);

        // Assert shipping address is set
        Assert.assertTrue(kraken.getShoppingHelper().isShippingAddressSet(),
                "Can't approve the shipping address was set correctly, check manually\n");

        //
        Assert.assertTrue(kraken.getShoppingHelper().currentShippingAddress().equals(address),
                "Current shipping address is not the same that was entered during the setting procedure\n");
    }


    @Test( enabled = false,
            description = "Тест изменения адреса доставки на витрине ритейлера",
            groups = {"acceptance","regression"},
            priority = 204
    )
    public void changeShippingAddress() throws Exception {
        kraken.get().retailerPage("metro");

        if(kraken.getShoppingHelper().isShippingAddressEmpty()){
            kraken.getShoppingHelper().setShippingAddress("Москва, ул Мусы Джалиля, д 19 к 1"); // TODO брать из Addresses
        }

        String initialAddress = kraken.getShoppingHelper().currentShippingAddress();
        final String newAddress1 = "Москва, ул Пироговская М., д 88"; // TODO брать из Addresses
        final String newAddress2 = "Москва, Ленинградское шоссе, д 50"; // TODO брать из Addresses

        // проверяем на совпадение с текущим адресом доставки и меняем адрес
        if(initialAddress.equals(newAddress1)){
            kraken.getShoppingHelper().changeShippingAddress(newAddress2);
        } else {
            kraken.getShoppingHelper().changeShippingAddress(newAddress1);
        }

        // Assert shipping address is set
        Assert.assertTrue(kraken.getShoppingHelper().isShippingAddressSet(),
                "Can't approve the shipping address was set correctly, check manually\n");

        // Assert shipping address was changed
        Assert.assertFalse(kraken.getShoppingHelper().currentShippingAddress().equals(initialAddress),
                "Shipping address was changed\n");
    }


    @Test( enabled = false,
            description = "Проверяем что на лендинге по адресу вне зоны доставки нет доступных магазинов",
            groups = {"acceptance","regression"},
            priority = 205
    )
    public void setAddressWithNoAvailableShops() throws Exception {
        kraken.get().retailerPage("metro");
        kraken.getShoppingHelper().changeShippingAddress("Москва, ул Лосиноостровская, д 1"); // TODO брать из Addresses
        kraken.getShoppingHelper().openShopsList();

        // Assert shops list is open
        Assert.assertTrue(kraken.getShoppingHelper().isShopsListOpen(),
                "Shops list isn't open\n");

        // Assert shops list is empty
        Assert.assertTrue(kraken.getShoppingHelper().isShopsListEmpty(),
                "Shipping address is out of the shipping zone, but the shops list isn't empty\n");
    }


    @Test( enabled = false,
            description = "Проверяем что после авторизации подтягивается адрес доствки",
            groups = {"acceptance","regression"},
            priority = 206
    )
    public void sucessShippingAddressFetchAfterAuthorisation() throws Exception {
        kraken.get().getLandingPage();
        kraken.getSessionHelper().doLoginAs("admin");

        // Assert shipping address is set after authorisation
        Assert.assertTrue(kraken.getShoppingHelper().isShippingAddressSet(),
                "Shipping address is not fetched after authorisation, check manually\n");

    }

    // TODO тест на изменение выбранного магазина

*/
}
