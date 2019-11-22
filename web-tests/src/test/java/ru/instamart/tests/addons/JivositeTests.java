package ru.instamart.tests.addons;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.application.AppManager;
import ru.instamart.application.lib.Pages;
import ru.instamart.application.platform.modules.Shop;
import ru.instamart.application.platform.modules.User;
import ru.instamart.tests.TestBase;

import static ru.instamart.application.Config.TestsConfiguration.AddonsTests.enableJivositeTests;

public class JivositeTests extends TestBase {

    @Test(  enabled = enableJivositeTests,
            description = "Тест отсутствия виджета Jivosite на лендинге",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 11101
    )
    public void noJivositeWidgetOnLanding() {
        User.Logout.quickly();

        Assert.assertFalse(kraken.detect().isJivositeWidgetAvailable(),
                "Виджет Jivosite доступен на лендинге");
    }

    @Test(  enabled = enableJivositeTests,
            description = "Тест отсутствия виджета Jivosite в чекауте",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 11102
    )
    public void noJivositeWidgetOnCheckout() {
        User.Do.loginAs(AppManager.session.admin);
        kraken.reach().checkout();

        Assert.assertFalse(kraken.detect().isJivositeWidgetAvailable(),
                "Виджет Jivosite доступен в чекауте");
    }

    @Test(  enabled = enableJivositeTests,
            description = "Тест работы с виджетом Jivosite на витрине ритейлера",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 11103
    )
    public void successOperateJivositeWidgetOnRetailerPage() {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().page("metro");

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

    @Test(  enabled = enableJivositeTests,
            description = "Тест работы с виджетом Jivosite в каталоге",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 11104
    )
    public void successOperateJivositeWidgetInCatalog() {
        SoftAssert softAssert = new SoftAssert();
        Shop.Search.item("хлеб");

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

    @Test(  enabled = enableJivositeTests,
            description = "Тест работы с виджетом Jivosite на странице 404",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 11105
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

    @Test(  enabled = enableJivositeTests,
            description = "Тест успешной отправки сообщения в Jivosite",
            groups = {"sbermarket-regression"},
            priority = 11106
    )
    public void successSendMessageToJivositeFromRetailerPage() {
        String testMessage = "тест";
        kraken.get().page("metro");

        Shop.Jivosite.sendMessage(testMessage);

        Assert.assertTrue(kraken.detect().isJivositeMessageSent(testMessage),
                "Не отправляется сообщение в Jivosite\n");
    }
}
