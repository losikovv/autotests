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
        kraken.shopping().openShopsList();

        // Assert shoplist is open
        Assert.assertTrue(kraken.shopping().isShopsListOpen(),
                "Can't open shoplist\n");

        // Assert shoplist isn't empty
        Assert.assertFalse(kraken.shopping().isShopsListEmpty(),
                "Shoplist is not supposed to be empty by default\n");

        // Assert there is at least one shop in the shoplist
        Assert.assertTrue(kraken.shopping().isAnyShopsAvailable(),
                "There is no shops available in the shoplist\n");

        kraken.shopping().closeShopSelector();

        // Assert shoplist isn't open
        Assert.assertFalse(kraken.shopping().isShopsListOpen(),
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
        kraken.shopping().set(address);
        kraken.shopping().selectShop(1);

        // Assert shipping address is set
        Assert.assertTrue(kraken.shopping().isShippingAddressSet(),
                "Can't approve the shipping address was set correctly, check manually\n");

        //
        Assert.assertTrue(kraken.shopping().currentShipAddress().equals(address),
                "Current shipping address is not the same that was entered during the setting procedure\n");
    }


    @Test( enabled = false,
            description = "Тест изменения адреса доставки на витрине ритейлера",
            groups = {"acceptance","regression"},
            priority = 204
    )
    public void change() throws Exception {
        kraken.get().retailerPage("metro");

        if(kraken.shopping().isShippingAddressEmpty()){
            kraken.shopping().set("Москва, ул Мусы Джалиля, д 19 к 1"); // TODO брать из Addresses
        }

        String initialAddress = kraken.shopping().currentShipAddress();
        final String newAddress1 = "Москва, ул Пироговская М., д 88"; // TODO брать из Addresses
        final String newAddress2 = "Москва, Ленинградское шоссе, д 50"; // TODO брать из Addresses

        // проверяем на совпадение с текущим адресом доставки и меняем адрес
        if(initialAddress.equals(newAddress1)){
            kraken.shopping().change(newAddress2);
        } else {
            kraken.shopping().change(newAddress1);
        }

        // Assert shipping address is set
        Assert.assertTrue(kraken.shopping().isShippingAddressSet(),
                "Can't approve the shipping address was set correctly, check manually\n");

        // Assert shipping address was changed
        Assert.assertFalse(kraken.shopping().currentShipAddress().equals(initialAddress),
                "Shipping address was changed\n");
    }


    @Test( enabled = false,
            description = "Проверяем что на лендинге по адресу вне зоны доставки нет доступных магазинов",
            groups = {"acceptance","regression"},
            priority = 205
    )
    public void setAddressWithNoAvailableShops() throws Exception {
        kraken.get().retailerPage("metro");
        kraken.shopping().change("Москва, ул Лосиноостровская, д 1"); // TODO брать из Addresses
        kraken.shopping().openShopsList();

        // Assert shops list is open
        Assert.assertTrue(kraken.shopping().isShopsListOpen(),
                "Shops list isn't open\n");

        // Assert shops list is empty
        Assert.assertTrue(kraken.shopping().isShopsListEmpty(),
                "Shipping address is out of the shipping zone, but the shops list isn't empty\n");
    }


    @Test( enabled = false,
            description = "Проверяем что после авторизации подтягивается адрес доствки",
            groups = {"acceptance","regression"},
            priority = 206
    )
    public void sucessShippingAddressFetchAfterAuthorisation() throws Exception {
        kraken.get().getLandingPage();
        kraken.getSessionHelper().loginAs("admin");

        // Assert shipping address is set after authorisation
        Assert.assertTrue(kraken.shopping().isShippingAddressSet(),
                "Shipping address is not fetched after authorisation, check manually\n");

    }

    // TODO тест на изменение выбранного магазина

*/
}
