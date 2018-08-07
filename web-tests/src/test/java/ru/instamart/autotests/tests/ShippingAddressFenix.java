package ru.instamart.autotests.tests;



// Тесты адреса доставки по Фениксу



import org.testng.annotations.BeforeMethod;

public class ShippingAddressFenix extends TestBase{

    @BeforeMethod(alwaysRun = true)
    public void dropShipAddress() throws Exception {
        app.getNavigationHelper().getRetailerPage("metro");
        if(app.getSessionHelper().isUserAuthorised()) {
            app.getSessionHelper().doLogout();
        } else {
            app.getSessionHelper().doLoginAsAdmin();
            app.getSessionHelper().doLogout();
        }
    }

}
