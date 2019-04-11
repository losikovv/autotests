package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.autotests.application.Pages;

import static ru.instamart.autotests.application.Config.enableJivositeTests;
import static ru.instamart.autotests.appmanager.ApplicationManager.session;

public class Jivosite extends TestBase{


    @Test(  enabled = enableJivositeTests,
            description = "Тест отсутствия виджета Jivosite на лендинге",
            groups = {"acceptance","regression"},
            priority = 1201
    )
    public void noJivositeWidgetOnLanding () throws Exception {
        kraken.perform().quickLogout();

        Assert.assertFalse(kraken.detect().isJivositeWidgetAvailable(),
                "Виджет Jivosite доступен на лендинге");
    }


    @Test(  enabled = enableJivositeTests,
            description = "Тест отсутствия виджета Jivosite в чекауте",
            groups = {"acceptance","regression"},
            priority = 1202
    )
    public void noJivositeWidgetOnCheckout () throws Exception {
        kraken.perform().loginAs(session.admin);
        kraken.reach().checkout();

        Assert.assertFalse(kraken.detect().isJivositeWidgetAvailable(),
                "Виджет Jivosite доступен в чекауте");
    }


    @Test(  enabled = enableJivositeTests,
            description = "Тест работы с виджетом Jivosite на витрине ритейлера",
            groups = {"acceptance","regression"},
            priority = 1203
    )
    public void successOperateJivositeWidgetOnRetailerPage() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().page("metro");

        Assert.assertTrue(kraken.detect().isJivositeWidgetAvailable(),
                "Виджет Jivosite недоступен на витрине ритейлера\n");

        kraken.jivosite().open();

        softAssert.assertTrue(kraken.detect().isJivositeOpen(),
                "Не разворачивается виджет Jivosite на витрине ритейлера\n");

        kraken.jivosite().close();

        softAssert.assertFalse(kraken.detect().isJivositeOpen(),
                "Не сворачивается виджет Jivosite на витрине ритейлера\n");

        softAssert.assertAll();
    }


    @Test(  enabled = enableJivositeTests,
            description = "Тест работы с виджетом Jivosite в каталоге",
            groups = {"acceptance","regression"},
            priority = 1204
    )
    public void successOperateJivositeWidgetInCatalog() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        kraken.search().item("хлеб");

        Assert.assertTrue(kraken.detect().isJivositeWidgetAvailable(),
                "Виджет Jivosite недоступен в каталоге\n");

        kraken.jivosite().open();

        softAssert.assertTrue(kraken.detect().isJivositeOpen(),
                "Не разворачивается виджет Jivosite в каталоге\n");

        kraken.jivosite().close();

        softAssert.assertFalse(kraken.detect().isJivositeOpen(),
                "Не сворачивается виджет Jivosite в каталоге\n");

        softAssert.assertAll();
    }


    @Test(  enabled = enableJivositeTests,
            description = "Тест работы с виджетом Jivosite на странице 404",
            groups = {"acceptance","regression"},
            priority = 1205
    )
    public void successOperateJivositeWidgetOnPage404() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().page(Pages.page404());

        Assert.assertTrue(kraken.detect().isJivositeWidgetAvailable(),
                "Виджет Jivosite недоступен на странице 404\n");

        kraken.jivosite().open();

        softAssert.assertTrue(kraken.detect().isJivositeOpen(),
                "Не разворачивается виджет Jivosite на странице 404\n");

        kraken.jivosite().close();

        softAssert.assertFalse(kraken.detect().isJivositeOpen(),
                "Не сворачивается виджет Jivosite на странице 404\n");

        softAssert.assertAll();
    }


    @Test(  enabled = enableJivositeTests,
            description = "Тест успешной отправки сообщения в Jivosite",
            groups = {"regression"},
            priority = 1206
    )
    public void successSendMessageToJivositeFromRetailerPage() throws Exception {
        kraken.get().page("metro");

        kraken.jivosite().sendMessage("Тест");

        Assert.assertTrue(kraken.detect().isJivositeMessageSent(),
                "Не отправляется сообщение в Jivosite\n");
    }
}
