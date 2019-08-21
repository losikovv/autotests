package ru.instamart.autotests.tests.addons;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.autotests.application.Pages;
import ru.instamart.autotests.appmanager.ShopHelper;
import ru.instamart.autotests.tests.TestBase;

import static ru.instamart.autotests.application.Config.TestsConfiguration.AddonsTests.enableJivositeTests;
import static ru.instamart.autotests.appmanager.ApplicationManager.session;

public class JivositeTests extends TestBase {


    @Test(  enabled = enableJivositeTests,
            description = "Тест отсутствия виджета Jivosite на лендинге",
            groups = {"acceptance","regression"},
            priority = 11101
    )
    public void noJivositeWidgetOnLanding() {
        kraken.perform().quickLogout();

        Assert.assertFalse(kraken.detect().isJivositeWidgetAvailable(),
                "Виджет Jivosite доступен на лендинге");
    }


    @Test(  enabled = enableJivositeTests,
            description = "Тест отсутствия виджета Jivosite в чекауте",
            groups = {"acceptance","regression"},
            priority = 11102
    )
    public void noJivositeWidgetOnCheckout() {
        kraken.perform().loginAs(session.admin);
        kraken.reach().checkout();

        Assert.assertFalse(kraken.detect().isJivositeWidgetAvailable(),
                "Виджет Jivosite доступен в чекауте");
    }


    @Test(  enabled = enableJivositeTests,
            description = "Тест работы с виджетом Jivosite на витрине ритейлера",
            groups = {"acceptance","regression"},
            priority = 11103
    )
    public void successOperateJivositeWidgetOnRetailerPage() {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().page("metro");

        Assert.assertTrue(kraken.detect().isJivositeWidgetAvailable(),
                "Виджет Jivosite недоступен на витрине ритейлера\n");

        ShopHelper.Jivosite.open();

        softAssert.assertTrue(kraken.detect().isJivositeOpen(),
                "Не разворачивается виджет Jivosite на витрине ритейлера\n");

        ShopHelper.Jivosite.close();

        softAssert.assertFalse(kraken.detect().isJivositeOpen(),
                "Не сворачивается виджет Jivosite на витрине ритейлера\n");

        softAssert.assertAll();
    }


    @Test(  enabled = enableJivositeTests,
            description = "Тест работы с виджетом Jivosite в каталоге",
            groups = {"acceptance","regression"},
            priority = 11104
    )
    public void successOperateJivositeWidgetInCatalog() {
        SoftAssert softAssert = new SoftAssert();
        ShopHelper.Search.item("хлеб");

        Assert.assertTrue(kraken.detect().isJivositeWidgetAvailable(),
                "Виджет Jivosite недоступен в каталоге\n");

        ShopHelper.Jivosite.open();

        softAssert.assertTrue(kraken.detect().isJivositeOpen(),
                "Не разворачивается виджет Jivosite в каталоге\n");

        ShopHelper.Jivosite.close();

        softAssert.assertFalse(kraken.detect().isJivositeOpen(),
                "Не сворачивается виджет Jivosite в каталоге\n");

        softAssert.assertAll();
    }


    @Test(  enabled = enableJivositeTests,
            description = "Тест работы с виджетом Jivosite на странице 404",
            groups = {"acceptance","regression"},
            priority = 11105
    )
    public void successOperateJivositeWidgetOnPage404() {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().page(Pages.page404());

        Assert.assertTrue(kraken.detect().isJivositeWidgetAvailable(),
                "Виджет Jivosite недоступен на странице 404\n");

        ShopHelper.Jivosite.open();

        softAssert.assertTrue(kraken.detect().isJivositeOpen(),
                "Не разворачивается виджет Jivosite на странице 404\n");

        ShopHelper.Jivosite.close();

        softAssert.assertFalse(kraken.detect().isJivositeOpen(),
                "Не сворачивается виджет Jivosite на странице 404\n");

        softAssert.assertAll();
    }


    @Test(  enabled = enableJivositeTests,
            description = "Тест успешной отправки сообщения в Jivosite",
            groups = {"regression"},
            priority = 11106
    )
    public void successSendMessageToJivositeFromRetailerPage() {
        kraken.get().page("metro");

        ShopHelper.Jivosite.sendMessage("Тест");

        Assert.assertTrue(kraken.detect().isJivositeMessageSent(),
                "Не отправляется сообщение в Jivosite\n");
    }
}
