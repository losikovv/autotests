package ru.instamart.tests.ui.addons.RetailRocket;

import ru.instamart.ui.data.lib.Widgets;
import ru.instamart.ui.module.Shop;
import ru.instamart.ui.module.User;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.tests.ui.TestBase;

public class RetailRocketSeoCatalogWidgetsTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        User.Logout.quickly();
        kraken.get().page("categories/ovoshchi-i-frukty");
    }

    @BeforeMethod(alwaysRun = true,
            description ="Выполняем шаги предусловий для теста")
    public void beforeTest() {
        kraken.get().page("categories/ovoshchi-i-frukty");
    }


    @Test(  description = "Тест наличия виджета 'выбор покупатлей' в сео каталоге",
            groups = {"sbermarket-acceptance", "sbermarket-regression"}
    )
    public void successCheckCustomersChoiceWidget() {
        Assert.assertTrue(
                kraken.detect().isWidgetPresent(Widgets.RetailRocket.SeoCatalog.CustomersChoice()),
                    "Нет блока 'Выбор покупателей' в сео каталоге");
    }

    @Test(  description = "Тест открытия карточки товара из виджета 'Вы недавно смотрели'",
            groups = {"sbermarket-acceptance", "sbermarket-regression"},

            dependsOnMethods = "successCheckCustomersChoiceWidget"
    )
    public void successOpenItemCardFromCustomersChoiceWidget() {
        Shop.Catalog.Item.open(Widgets.RetailRocket.SeoCatalog.CustomersChoice());

        Assert.assertTrue(
                kraken.detect().isItemCardOpen(),
                    "Не открывается карточка товара из виджета 'Выбор покупателей' в сео каталоге");
    }
}


