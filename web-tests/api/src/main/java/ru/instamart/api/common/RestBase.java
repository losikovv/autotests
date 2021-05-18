package ru.instamart.api.common;

import ru.instamart.api.helper.ApiV3Helper;
import ru.instamart.api.helper.InstamartApiHelper;
import ru.instamart.api.helper.ShopperAppApiHelper;
import ru.instamart.kraken.helper.LogAttachmentHelper;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class RestBase {

    protected static final InstamartApiHelper apiV2 = new InstamartApiHelper();
    protected static final ShopperAppApiHelper shopper = new ShopperAppApiHelper();
    protected static final ApiV3Helper apiV3 = new ApiV3Helper();
    protected Response response;

    @BeforeMethod(alwaysRun = true,description = "Стартуем запись системного лога")
    public void captureStart(){
        LogAttachmentHelper.start();
    }

    @AfterMethod(alwaysRun = true,description = "Добавляем системный лог к тесту")
    public void captureFinish() {
        final String result = LogAttachmentHelper.getContent();
        LogAttachmentHelper.stop();
        Allure.addAttachment("Системный лог теста", result);
    }
}
