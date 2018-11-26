package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.autotests.testdata.Addresses;



// Тесты адреса доставки по Фениксу



public class ShippingAddress extends TestBase{

    /*
    @BeforeMethod(alwaysRun = true)
    public void dropShipAddress() throws Exception {
        kraken.getNavigationHelper().getRetailerPage("metro");
        if(kraken.getSessionHelper().isUserAuthorised()) {
            kraken.getSessionHelper().doLogout();
        } else {
            kraken.getSessionHelper().doLoginAs("user");
            kraken.getSessionHelper().doLogout();
        }
    }
*/


    @Test(
            description = "Проверяем что по дефолту на витрине ритейлера не выбран адрес",
            groups = {"acceptance","regression"},
            priority = 201
    )
    public void emptyShippingAddressByDefault() throws Exception {
        kraken.getNavigationHelper().getRetailerPage("metro");

        Assert.assertTrue(kraken.getShoppingHelper().isShippingAddressEmpty(),
                "Shipping address is not empty by default\n");

        Assert.assertFalse(kraken.getShoppingHelper().isShippingAddressSet(),
                "Shipping address is set by default\n");
    }


    @Test(
            description = "Тест ввода адреса доставки на витрине ритейлера",
            groups = {"acceptance","regression"},
            priority = 202
    )
    public void setShippingAddressOnRetailerPage() throws Exception {
        kraken.getNavigationHelper().getRetailerPage("metro");
        kraken.getShoppingHelper().setShippingAddress(Addresses.Moscow.defaultAddress());

        Assert.assertTrue(kraken.getShoppingHelper().isShippingAddressSet(),
                "Can't approve the shipping address was set correctly, check manually\n");

         Assert.assertTrue(kraken.getShoppingHelper().currentShippingAddress().equals(Addresses.Moscow.defaultAddress()),
               "Current shipping address is not the same that was entered during the setting procedure\n");
    }


    @Test(
            description = "Тест изменения адреса доставки",
            groups = {"acceptance","regression"},
            priority = 203
    )
    public void changeShippingAddress() throws Exception {
        kraken.getNavigationHelper().getRetailerPage("metro");
        kraken.getShoppingHelper().changeShippingAddress(Addresses.Moscow.testAddress());

        Assert.assertTrue(kraken.getShoppingHelper().isShippingAddressSet(),
                "Can't approve the shipping address was set correctly, check manually\n");

        Assert.assertTrue(kraken.getShoppingHelper().currentShippingAddress().equals(Addresses.Moscow.testAddress()),
                "Current shipping address is not the same that was entered during the setting procedure\n");
    }




}
