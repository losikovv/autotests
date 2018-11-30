package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.Addresses;


// Тесты адреса доставки по Фениксу


public class ShippingAddress extends TestBase{


    @Test(
            description = "Проверяем что по дефолту на витрине ритейлера не выбран адрес",
            groups = {"acceptance","regression"},
            priority = 201
    )
    public void emptyShippingAddressByDefault() throws Exception {
        kraken.get().page("metro");

        Assert.assertTrue(kraken.detect().isShippingAddressEmpty(),
                "Shipping address is not empty by default\n");

        Assert.assertFalse(kraken.detect().isShippingAddressSet(),
                "Shipping address is set by default\n");
    }


    @Test(
            description = "Тест ввода адреса доставки на витрине ритейлера",
            groups = {"acceptance","regression"},
            priority = 202
    )
    public void setShippingAddressOnRetailerPage() throws Exception {
        kraken.get().page("metro");
        kraken.shipAddress().set(Addresses.Moscow.defaultAddress());

        Assert.assertTrue(kraken.detect().isShippingAddressSet(),
                "Can't approve the shipping address was set correctly, check manually\n");

         Assert.assertTrue(kraken.grab().currentShipAddress().equals(Addresses.Moscow.defaultAddress()),
               "Current shipping address is not the same that was entered during the setting procedure\n");
    }


    @Test(
            description = "Тест изменения адреса доставки",
            groups = {"acceptance","regression"},
            priority = 203
    )
    public void changeShippingAddress() throws Exception {
        kraken.get().page("metro");
        kraken.shipAddress().change(Addresses.Moscow.testAddress());

        Assert.assertTrue(kraken.detect().isShippingAddressSet(),
                "Can't approve the shipping address was set correctly, check manually\n");

        Assert.assertTrue(kraken.grab().currentShipAddress().equals(Addresses.Moscow.testAddress()),
                "Current shipping address is not the same that was entered during the setting procedure\n");
    }

}
