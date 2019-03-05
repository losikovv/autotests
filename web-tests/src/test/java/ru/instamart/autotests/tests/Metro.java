package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Pages;
import ru.instamart.autotests.application.Users;
import ru.instamart.autotests.models.EnvironmentData;

import static ru.instamart.autotests.appmanager.ApplicationManager.session;

public class Metro extends TestBase {


    @BeforeMethod(alwaysRun = true)
    public void quickLogout() {
        kraken.perform().quickLogout();
    }

    @Test(  description = "Доступность нового футера на деливери метро",
            groups = {"acceptance","regression"},
            priority = 1488
    )
    public void newTenantFooter() throws Exception {
        kraken.get().baseUrl();

        Assert.assertTrue(kraken.detect().isTenantFooterAvailable(),
                "Тенант-футер недоступен на деливери метро");
    }


    @Test(  description = "Отсутствие селектора магазинов на деливери метро",
            groups = {"acceptance","regression"},
            priority = 1489
    )
    public void outStoreSelectorOnTenant() throws Exception {
        kraken.get().baseUrl();

        Assert.assertFalse(kraken.detect().isStoreSelectorAvailaible(),
                "Селектор магазинов доступен на деливери метро");
    }

    @Test(  description = "Отсутствие селектора магазинов на деливери метро",
            groups = {"acceptance","regression"},
            priority = 1490
    )
    public void outContactsOnTenantProfile() throws Exception {
        kraken.get().baseUrl();
        kraken.perform().loginAs(session.user);

        Assert.assertFalse(kraken.detect().isContactsOnProfileAvailaible(),
                "Контакты доступны в меню профиля на деливери метро");
    }

    @Test(  description = "Отсутствие живосайта на витрине деливери метро",
            groups = {"acceptance","regression"},
            priority = 1491
    )
    public void outJivositeOnTenant() throws Exception {
        kraken.get().baseUrl();

        Assert.assertFalse(kraken.detect().isJivositeWidgetAvailable(),
                "Виджет живосайт доступен на деливери метро\n");
    }

    @Test(  description = "Отсутствие живосайта в чекауте деливери метро",
            groups = {"acceptance","regression"},
            priority = 1492
    )
    public void outJivositeOnTenantCheckout() throws Exception {
        kraken.get().baseUrl();
        kraken.perform().loginAs(session.user);
        kraken.perform().reachCheckout();

        Assert.assertFalse(kraken.detect().isJivositeWidgetAvailable(),
                "Виджет живосайт доступен в метро-чекауте\n");
    }

    @Test(  description = "Отсутствие живосайта на странице заказов деливери метро",
            groups = {"acceptance","regression"},
            priority = 1493
    )
    public void outJivositeOnTenantOrders() throws Exception {
        kraken.get().baseUrl();
        kraken.perform().loginAs(session.user);
        kraken.get().ordersPage();

        Assert.assertFalse(kraken.detect().isJivositeWidgetAvailable(),
                "Виджет живосайт доступен на странице заказов деливери метро\n");
    }

    @Test(  description = "Отсутствие живосайта на странице профиля деливери метро",
            groups = {"acceptance","regression"},
            priority = 1494
    )
    public void outJivositeOnTenantProfile() throws Exception {
        kraken.get().baseUrl();
        kraken.perform().loginAs(session.user);
        kraken.get().profilePage();

        Assert.assertFalse(kraken.detect().isJivositeWidgetAvailable(),
                "Виджет живосайт доступен на странице профиля деливери метро");
    }

    @Test(  description = "Отсутствие живосайта на странице профиля деливери метро",
            groups = {"acceptance","regression"},
            priority = 1495
    )
    public void outJivositeOnTenantFavoritesPage() throws Exception {
        kraken.get().baseUrl();
        kraken.perform().loginAs(session.user);
        kraken.get().favoritesPage();

        Assert.assertFalse(kraken.detect().isJivositeWidgetAvailable(),
                "Виджет живосайт доступен в избранном на деливери метро\n");
    }

    @Test(  description = "Отсутствие живосайта на странице профиля деливери метро",
            groups = {"acceptance","regression"},
            priority = 1496
    )
    public void outJivositeOnTenantAddressesPage() throws Exception {
        kraken.get().baseUrl();
        kraken.perform().loginAs(session.user);
        kraken.get().addressesPage();

        Assert.assertFalse(kraken.detect().isJivositeWidgetAvailable(),
                "Виджет живосайт доступен на странице адресов юзера на деливери метро");
    }

    @Test(  description = "Отсутствие программ лояльности в чекауте деливери метро",
            groups = {"acceptance","regression"},
            priority = 1497
    )
    public void outLoyaltyProgramsOnTenantCheckout() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().baseUrl();
        kraken.perform().loginAs(session.user);
        kraken.perform().reachCheckout();

        softAssert.assertFalse(kraken.detect().isElementDisplayed(Elements.Site.Checkout.loyaltyPrograms()),
                "Программы лояльности доступны в чекауте деливери метро");

        kraken.get().baseUrl();
        kraken.drop().cart();

        softAssert.assertAll();
    }
}
