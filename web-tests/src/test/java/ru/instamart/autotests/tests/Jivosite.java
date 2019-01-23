package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class Jivosite extends TestBase{


    @Test(
            description = "Тест работы с виджетом Jivosite",
            groups = {"acceptance","regression"},
            priority = 1201
    )
    public void successOperateJivositeWidgetOnRetailerPage() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().page("metro");

        kraken.jivosite().open();

        softAssert.assertTrue(kraken.detect().isJivositeOpen(),
                "Не разворачивается виджет Jivosite\n");

        kraken.jivosite().close();

        softAssert.assertFalse(kraken.detect().isJivositeOpen(),
                "Не сворачивается виджет Jivosite\n");

        softAssert.assertAll();
    }


    @Test(
            description = "Тест успешной отправки сообщения в Jivosite",
            groups = {"acceptance","regression"},
            priority = 1202
    )
    public void successSendMessageToJivosite() throws Exception {
        kraken.get().page("metro");

        kraken.jivosite().sendMessage("test");

        Assert.assertTrue(kraken.detect().isJivositeMessageSent(),
                "Не отправляется сообщение в Jivosite\n");
    }
}
