package instamart.api.common;

import instamart.api.helpers.InstamartApiHelper;
import instamart.api.helpers.ShopperApiHelper;
import instamart.core.helpers.LogAttachmentHelper;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

public class RestBase {

    protected static final InstamartApiHelper apiV2 = new InstamartApiHelper();
    protected static final ShopperApiHelper shopper = new ShopperApiHelper();
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
