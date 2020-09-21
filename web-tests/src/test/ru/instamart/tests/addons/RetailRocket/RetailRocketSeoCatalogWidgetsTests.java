package ru.instamart.tests.addons.RetailRocket;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import instamart.ui.modules.User;
import instamart.ui.common.lib.Widgets;
import instamart.ui.modules.Shop;
import ru.instamart.tests.TestBase;

import static instamart.core.settings.Config.TestsConfiguration.AddonsTests.enableRetailRocketTest;

public class RetailRocketSeoCatalogWidgetsTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        User.Logout.quickly();
        kraken.get().page("categories/ovoshchi-i-frukty");
    }

    @BeforeMethod(alwaysRun = true)
    public void preconditions () {
        kraken.get().page("categories/ovoshchi-i-frukty");
    }

    @Test(enabled = enableRetailRocketTest,
            description = "Тест наличия виджета 'выбор покупатлей' в сео каталоге",
            groups = {"sbermarket-acceptance", "sbermarket-regression"},
            priority = 12501
    )
    public void successCheckCustomersChoiceWidget() {
        Assert.assertTrue(
                kraken.detect().isWidgetPresent(Widgets.RetailRocket.SeoCatalog.CustomersChoice()),
                    "Нет блока 'Выбор покупателей' в сео каталоге");
    }

    @Test(enabled = enableRetailRocketTest,
            description = "Тест открытия карточки товара из виджета 'Вы недавно смотрели'",
            groups = {"sbermarket-acceptance", "sbermarket-regression"},
            priority = 12502,
            dependsOnMethods = "successCheckCustomersChoiceWidget"
    )
    public void successOpenItemCardFromCustomersChoiceWidget() {
        Shop.Catalog.Item.open(Widgets.RetailRocket.SeoCatalog.CustomersChoice());

        Assert.assertTrue(
                kraken.detect().isItemCardOpen(),
                    "Не открывается карточка товара из виджета 'Выбор покупателей' в сео каталоге");
    }
}


