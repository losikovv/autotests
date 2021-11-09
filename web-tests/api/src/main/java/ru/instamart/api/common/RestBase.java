package ru.instamart.api.common;

import io.kubernetes.client.PortForward;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import ru.instamart.api.helper.*;
import ru.instamart.api.k8s.K8sPortForward;
import ru.instamart.kraken.helper.LogAttachmentHelper;

public class RestBase {
    public static final PortForward.PortForwardResult connection = K8sPortForward.getInstance().portForwardMySQL();
    protected static final InstamartApiHelper apiV2 = new InstamartApiHelper();
    protected static final ShopperAppApiHelper shopperApp = new ShopperAppApiHelper();
    protected static final ApiV3Helper apiV3 = new ApiV3Helper();
    protected static final DeliveryClubHelper dc = new DeliveryClubHelper();
    protected final ShopperAdminApiHelper shopperAdmin = new ShopperAdminApiHelper();
    protected Response response;

    @BeforeMethod(alwaysRun = true, description = "Стартуем запись системного лога")
    public void captureStart() {
        LogAttachmentHelper.start();
    }

    @AfterMethod(alwaysRun = true, description = "Добавляем системный лог к тесту")
    public void captureFinish() {
        final String result = LogAttachmentHelper.getContent();
        LogAttachmentHelper.stop();
        Allure.addAttachment("Системный лог теста", result);
    }
}
