package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.autotests.application.Elements;

import static ru.instamart.autotests.appmanager.ApplicationManager.session;

public class Metro extends TestBase {


    @BeforeMethod(alwaysRun = true)
    public void quickLogout() {
        kraken.perform().quickLogout();
    }

    @Test(  description = "Доступность нового футера на деливери метро",
            groups = {"regression"},
            priority = 9001
    )
    public void newTenantFooter() {
        skipOn("instamart");
        kraken.get().baseUrl();

        //Assert.assertTrue(kraken.detect().isTenantFooterAvailable(), "Тенант-футер недоступен на деливери метро");
    }


    @Test(  description = "Отсутствие селектора магазинов на деливери метро",
            groups = {"regression"},
            priority = 9002
    )
    public void outStoreSelectorOnTenant() {
        skipOn("instamart");
        kraken.get().baseUrl();

        Assert.assertFalse(kraken.detect().isStoreButtonDisplayed(),
                "Селектор магазинов доступен на деливери метро");
    }

    @Test(  description = "Отсутствие селектора магазинов на деливери метро",
            groups = {"regression"},
            priority = 9003
    )
    public void outContactsOnTenantProfile() {
        skipOn("instamart");
        kraken.get().baseUrl();
        kraken.perform().loginAs(session.user);

        //Assert.assertFalse(kraken.detect().isContactsOnProfileAvailaible(), "Контакты доступны в меню профиля на деливери метро");
    }

    @Test(  description = "Отсутствие живосайта на витрине деливери метро",
            groups = {"regression"},
            priority = 9004
    )
    public void outJivositeOnTenant() {
        skipOn("instamart");
        kraken.get().baseUrl();

        Assert.assertFalse(kraken.detect().isJivositeWidgetAvailable(),
                "Виджет живосайт доступен на деливери метро\n");
    }

    @Test(  description = "Отсутствие живосайта в чекауте деливери метро",
            groups = {"regression"},
            priority = 9005
    )
    public void outJivositeOnTenantCheckout() {
        skipOn("instamart");
        kraken.get().baseUrl();
        kraken.perform().loginAs(session.user);
        kraken.reach().checkout();

        Assert.assertFalse(kraken.detect().isJivositeWidgetAvailable(),
                "Виджет живосайт доступен в метро-чекауте\n");
    }

    @Test(  description = "Отсутствие живосайта на странице заказов деливери метро",
            groups = {"regression"},
            priority = 9006
    )
    public void outJivositeOnTenantOrders() {
        skipOn("instamart");
        kraken.get().baseUrl();
        kraken.perform().loginAs(session.user);
        kraken.get().ordersPage();

        Assert.assertFalse(kraken.detect().isJivositeWidgetAvailable(),
                "Виджет живосайт доступен на странице заказов деливери метро\n");
    }

    @Test(  description = "Отсутствие живосайта на странице профиля деливери метро",
            groups = {"regression"},
            priority = 9007
    )
    public void outJivositeOnTenantProfile() {
        skipOn("instamart");
        kraken.get().baseUrl();
        kraken.perform().loginAs(session.user);
        kraken.get().profilePage();

        Assert.assertFalse(kraken.detect().isJivositeWidgetAvailable(),
                "Виджет живосайт доступен на странице профиля деливери метро");
    }

    @Test(  description = "Отсутствие живосайта на странице профиля деливери метро",
            groups = {"regression"},
            priority = 9008
    )
    public void outJivositeOnTenantFavoritesPage() {
        skipOn("instamart");
        kraken.get().baseUrl();
        kraken.perform().loginAs(session.user);
        kraken.get().favoritesPage();

        Assert.assertFalse(kraken.detect().isJivositeWidgetAvailable(),
                "Виджет живосайт доступен в избранном на деливери метро\n");
    }

    @Test(  description = "Отсутствие живосайта на странице профиля деливери метро",
            groups = {"regression"},
            priority = 9009
    )
    public void outJivositeOnTenantAddressesPage() {
        skipOn("instamart");
        kraken.get().baseUrl();
        kraken.perform().loginAs(session.user);
        kraken.get().addressesPage();

        Assert.assertFalse(kraken.detect().isJivositeWidgetAvailable(),
                "Виджет живосайт доступен на странице адресов юзера на деливери метро");
    }

    @Test(  description = "Тест отсутствия бонусных программ в чекауте delivery.metro-cc",
            groups = {"metro"},
            priority = 9010
    )
    public void noBonusProgramsOnTenantCheckout() {
        skipOn("instamart");
        SoftAssert softAssert = new SoftAssert();

        kraken.get().baseUrl();
        kraken.perform().loginAs(session.user);
        kraken.reach().checkout();

        softAssert.assertFalse(
                kraken.detect().isElementDisplayed(Elements.Checkout.Bonus.programsList()),
                    "Программы лояльности доступны в чекауте delivery.metro-cc");

        kraken.get().baseUrl();
        kraken.drop().cart();

        softAssert.assertAll();
    }
}
