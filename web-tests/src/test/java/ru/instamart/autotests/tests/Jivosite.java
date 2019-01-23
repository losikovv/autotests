package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.autotests.application.Pages;

public class Jivosite extends TestBase{


    @BeforeMethod(alwaysRun = true)
    public void quickLogout() throws Exception {
        kraken.perform().quickLogout();
    }

    @Test(
            description = "Тест разворачивания виджета Jivosite",
            groups = {"acceptance","regression"},
            priority = 1201
    )
    public void openWidgetJivosite() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().page("metro");
        kraken.jivosite().open();

        softAssert.assertTrue(kraken.detect().isJivositeOpen(),
                "Не открывается виджет Jivosite\n");
    }

    @Test(
            description = "Тест сворачивания виджета Jivosite",
            groups = {"acceptance","regression"},
            priority = 1202
    )
    public void closeWidgetJivosite() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().page("metro");
        kraken.jivosite().open();
        kraken.jivosite().close();

        softAssert.assertFalse(kraken.detect().isJivositeOpen(),
                "Не закрывается виджет Jivosite");
    }

    @Test(
            description = "Тест отправки сообщения в виджете Jivosite",
            groups = {"acceptance","regression"},
            priority = 1203
    )
    public void sendMessageJivosite() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().page("metro");
        kraken.jivosite().sendMessage("test");

        softAssert.assertTrue(kraken.detect().isJivositeMessageSent(),
                "Не отправлено сообщение в Jivosite");
    }
}
