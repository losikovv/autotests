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

        Assert.assertFalse(kraken.detect().isShippingAddressSet(),
                "Shipping address is set by default\n");
    }


    @Test(
            description = "Тест ввода адреса доставки на витрине ритейлера",
            groups = {"acceptance","regression"},
            priority = 203
    )
    public void setShippingAddressOnRetailerPage() throws Exception {
        kraken.get().page("metro");
        kraken.shipAddress().set(Addresses.Moscow.defaultAddress());

        Assert.assertTrue(kraken.detect().isShippingAddressSet(),
                "Can't approve the shipping address was set correctly, check manually\n");

        Assert.assertEquals(kraken.grab().currentShipAddress(), Addresses.Moscow.defaultAddress(),
                "Current shipping address is not the same that was entered during the setting procedure\n");
    }

    @Test(
            description = "Тест отмены ввода адреса доставки на витрине ритейлера",
            groups = {"regression"},
            priority = 202
    )
    public void cancelSetShippingAddressOnRetailerPage() throws Exception {
        kraken.get().page("metro");
        kraken.shipAddress().openAddressModal();
        kraken.shipAddress().fill(Addresses.Moscow.defaultAddress());
        kraken.shipAddress().closeAddressModal();

        Assert.assertFalse(kraken.detect().isShippingAddressSet(),
                "Can't approve the shipping address was remained correctly, check manually\n");
    }


    @Test(
            description = "Тест изменения адреса доставки",
            groups = {"acceptance","regression"},
            priority = 205
    )
    public void changeShippingAddress() throws Exception {
        kraken.get().page("metro");
        if(!kraken.detect().isShippingAddressSet()) {
            kraken.shipAddress().set(Addresses.Moscow.defaultAddress());
        }

        kraken.shipAddress().change(Addresses.Moscow.testAddress());

        Assert.assertTrue(kraken.detect().isShippingAddressSet(),
                "Can't approve the shipping address was set correctly, check manually\n");

        Assert.assertEquals(kraken.grab().currentShipAddress(), Addresses.Moscow.testAddress(),
                "Current shipping address is not the same that was entered during the setting procedure\n");
    }

    @Test(
            description = "Тест отмены изменения адреса доставки",
            groups = {"regression"},
            priority = 204
    )
    public void cancelChangeShippingAddress() throws Exception {
        kraken.get().page("metro");
        kraken.shipAddress().openAddressModal();
        kraken.shipAddress().fill(Addresses.Moscow.testAddress());
        kraken.shipAddress().closeAddressModal();


        Assert.assertTrue(kraken.detect().isShippingAddressSet(),
                "Can't approve the shipping address was remained correctly, check manually\n");

        Assert.assertNotEquals(kraken.grab().currentShipAddress(), Addresses.Moscow.testAddress(),
                "Current shipping address is the same that was entered during the setting procedure\n");
    }


}
