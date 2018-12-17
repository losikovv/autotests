package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;



    // Тесты адреса доставки



public class ShippingAddressOLD {

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


*/
}
