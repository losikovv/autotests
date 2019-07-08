package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.Widgets;
import ru.instamart.autotests.appmanager.ShopHelper;

import static ru.instamart.autotests.application.Config.testRetailRocket;

public class RetailRocket_SeoCatalog extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        kraken.perform().quickLogout();
        kraken.get().page("categories/ovoshchi-i-frukty");
    }

    @BeforeMethod(alwaysRun = true)
    public void preconditions () {
        kraken.get().page("categories/ovoshchi-i-frukty");
    }

    @Test(enabled = testRetailRocket,
            description = "Тест наличия виджета 'выбор покупатлей' в сео каталоге",
            groups = {"acceptance", "regression"},
            priority = 11501
    )
    public void successCheckCustomersChoiceWidget() {
        Assert.assertTrue(
                kraken.detect().isWidgetPresent(Widgets.RetailRocket.SeoCatalog.CustomersChoice()),
                    "Нет блока 'Выбор покупателей' в сео каталоге");
    }

    @Test(enabled = testRetailRocket,
            description = "Тест открытия карточки товара из виджета 'Вы недавно смотрели'",
            groups = {"acceptance", "regression"},
            priority = 11502,
            dependsOnMethods = "successCheckCustomersChoiceWidget"
    )
    public void successOpenItemCardFromCustomersChoiceWidget() {
        ShopHelper.Catalog.Item.open(Widgets.RetailRocket.SeoCatalog.CustomersChoice());

        Assert.assertTrue(
                kraken.detect().isItemCardOpen(),
                    "Не открывается карточка товара из виджета 'Выбор покупателей' в сео каталоге");
    }
}


