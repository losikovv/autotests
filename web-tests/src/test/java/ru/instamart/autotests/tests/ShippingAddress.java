package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.testdata.Addresses;



// Тесты адреса доставки по Фениксу



public class ShippingAddress extends TestBase{

    /*
    @BeforeMethod(alwaysRun = true)
    public void dropShipAddress() throws Exception {
        app.getNavigationHelper().getRetailerPage("metro");
        if(app.getSessionHelper().isUserAuthorised()) {
            app.getSessionHelper().doLogout();
        } else {
            app.getSessionHelper().doLoginAs("user");
            app.getSessionHelper().doLogout();
        }
    }
*/


    @Test(
            description = "Проверяем что по дефолту на витрине ритейлера не выбран адрес",
            groups = {"acceptance","regression"},
            priority = 201
    )
    public void emptyShippingAddressByDefault() throws Exception {
        app.getNavigationHelper().getRetailerPage("metro");

        Assert.assertTrue(app.getShoppingHelper().isShippingAddressEmpty(),
                "Shipping address is not empty by default\n");

        Assert.assertFalse(app.getShoppingHelper().isShippingAddressSet(),
                "Shipping address is set by default\n");
    }


    @Test(
            description = "Тест ввода адреса доставки на витрине ритейлера",
            groups = {"acceptance","regression"},
            priority = 202
    )
    public void setShippingAddressOnRetailerPage() throws Exception {
        app.getNavigationHelper().getRetailerPage("metro");
        app.getShoppingHelper().setShippingAddress(Addresses.Moscow.defaultAddress());

        Assert.assertTrue(app.getShoppingHelper().isShippingAddressSet(),
                "Can't approve the shipping address was set correctly, check manually\n");

         Assert.assertTrue(app.getShoppingHelper().currentShippingAddress().equals(Addresses.Moscow.defaultAddress()),
               "Current shipping address is not the same that was entered during the setting procedure\n");
    }


    @Test(
            description = "Тест изменения адреса доставки",
            groups = {"acceptance","regression"},
            priority = 203
    )
    public void changeShippingAddress() throws Exception {
        app.getNavigationHelper().getRetailerPage("metro");
        app.getShoppingHelper().changeShippingAddress(Addresses.Moscow.testAddress());

        Assert.assertTrue(app.getShoppingHelper().isShippingAddressSet(),
                "Can't approve the shipping address was set correctly, check manually\n");

        Assert.assertTrue(app.getShoppingHelper().currentShippingAddress().equals(Addresses.Moscow.testAddress()),
                "Current shipping address is not the same that was entered during the setting procedure\n");
    }


}
