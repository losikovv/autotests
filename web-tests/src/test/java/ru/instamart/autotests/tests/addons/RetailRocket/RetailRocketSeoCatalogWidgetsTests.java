package ru.instamart.autotests.tests.addons.RetailRocket;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.Widgets;
import ru.instamart.autotests.appmanager.ShopHelper;
import ru.instamart.autotests.appmanager.User;
import ru.instamart.autotests.tests.TestBase;

import static ru.instamart.autotests.application.Config.TestsConfiguration.AddonsTests.enableRetailRocketTest;

public class RetailRocketSeoCatalogWidgetsTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        User.Do.quickLogout();
        kraken.get().page("categories/ovoshchi-i-frukty");
    }

    @BeforeMethod(alwaysRun = true)
    public void preconditions () {
        kraken.get().page("categories/ovoshchi-i-frukty");
    }

    @Test(enabled = enableRetailRocketTest,
            description = "Тест наличия виджета 'выбор покупатлей' в сео каталоге",
            groups = {"acceptance", "regression"},
            priority = 12501
    )
    public void successCheckCustomersChoiceWidget() {
        Assert.assertTrue(
                kraken.detect().isWidgetPresent(Widgets.RetailRocket.SeoCatalog.CustomersChoice()),
                    "Нет блока 'Выбор покупателей' в сео каталоге");
    }

    @Test(enabled = enableRetailRocketTest,
            description = "Тест открытия карточки товара из виджета 'Вы недавно смотрели'",
            groups = {"acceptance", "regression"},
            priority = 12502,
            dependsOnMethods = "successCheckCustomersChoiceWidget"
    )
    public void successOpenItemCardFromCustomersChoiceWidget() {
        ShopHelper.Catalog.Item.open(Widgets.RetailRocket.SeoCatalog.CustomersChoice());

        Assert.assertTrue(
                kraken.detect().isItemCardOpen(),
                    "Не открывается карточка товара из виджета 'Выбор покупателей' в сео каталоге");
    }
}


