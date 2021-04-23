package ru.instamart.tests.ui.addons;

import ru.instamart.core.settings.Config;
import ru.instamart.core.testdata.TestVariables;
import ru.instamart.core.testdata.UserManager;
import ru.instamart.ui.common.lib.Pages;
import ru.instamart.ui.modules.Shop;
import ru.instamart.ui.modules.User;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.tests.ui.TestBase;

public class JivositeTests extends TestBase {

    @Test(  description = "Тест отсутствия виджета Jivosite на лендинге",
            groups = {"sbermarket-acceptance","sbermarket-regression",}
    )
    public void noJivositeWidgetOnLanding() {
        User.Logout.quickly();

        Assert.assertFalse(kraken.detect().isJivositeWidgetAvailable(),
                "Виджет Jivosite доступен на лендинге");
    }

    @Test(  description = "Тест отсутствия виджета Jivosite в чекауте",
            groups = {"sbermarket-acceptance","sbermarket-regression",}
    )
    public void noJivositeWidgetOnCheckout() {
        User.Do.loginAs(UserManager.getDefaultAdmin());
        kraken.reach().checkout();

        Assert.assertFalse(kraken.detect().isJivositeWidgetAvailable(),
                "Виджет Jivosite доступен в чекауте");
    }

    @Test(  description = "Тест работы с виджетом Jivosite на витрине ритейлера",
            groups = {"sbermarket-acceptance","sbermarket-regression",}
    )
    public void successOperateJivositeWidgetOnRetailerPage() {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().page(Config.DEFAULT_RETAILER);

        Assert.assertTrue(kraken.detect().isJivositeWidgetAvailable(),
                "Виджет Jivosite недоступен на витрине ритейлера\n");

        Shop.Jivosite.open();

        softAssert.assertTrue(kraken.detect().isJivositeOpen(),
                "Не разворачивается виджет Jivosite на витрине ритейлера\n");

        Shop.Jivosite.close();

        softAssert.assertFalse(kraken.detect().isJivositeOpen(),
                "Не сворачивается виджет Jivosite на витрине ритейлера\n");

        softAssert.assertAll();
    }

    @Test(  description = "Тест работы с виджетом Jivosite в каталоге",
            groups = {"sbermarket-acceptance","sbermarket-regression",}
    )
    public void successOperateJivositeWidgetInCatalog() {
        SoftAssert softAssert = new SoftAssert();
        Shop.Search.searchItem(TestVariables.TestParams.ItemSearch.testQuery);

        Assert.assertTrue(kraken.detect().isJivositeWidgetAvailable(),
                "Виджет Jivosite недоступен в каталоге\n");

        Shop.Jivosite.open();

        softAssert.assertTrue(kraken.detect().isJivositeOpen(),
                "Не разворачивается виджет Jivosite в каталоге\n");

        Shop.Jivosite.close();

        softAssert.assertFalse(kraken.detect().isJivositeOpen(),
                "Не сворачивается виджет Jivosite в каталоге\n");

        softAssert.assertAll();
    }

    @Test(  description = "Тест работы с виджетом Jivosite на странице 404",
            groups = {"sbermarket-acceptance","sbermarket-regression",}
    )
    public void successOperateJivositeWidgetOnPage404() {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().page(Pages.page404());

        Assert.assertTrue(kraken.detect().isJivositeWidgetAvailable(),
                "Виджет Jivosite недоступен на странице 404\n");

        Shop.Jivosite.open();

        softAssert.assertTrue(kraken.detect().isJivositeOpen(),
                "Не разворачивается виджет Jivosite на странице 404\n");

        Shop.Jivosite.close();

        softAssert.assertFalse(kraken.detect().isJivositeOpen(),
                "Не сворачивается виджет Jivosite на странице 404\n");

        softAssert.assertAll();
    }

    @Test(  description = "Тест успешной отправки сообщения в Jivosite",
            groups = {"sbermarket-regression",}
    )
    public void successSendMessageToJivositeFromRetailerPage() {
        String testMessage = "тест";
        kraken.get().page(Config.DEFAULT_RETAILER);

        Shop.Jivosite.sendMessage(testMessage);

        Assert.assertTrue(kraken.detect().isJivositeMessageSent(testMessage),
                "Не отправляется сообщение в Jivosite\n");
    }
}
