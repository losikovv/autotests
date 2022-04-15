package ru.instamart.test.api.admin_api;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v1.AdjustmentV1;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.request.v1.AdjustmentsV1Request;
import ru.instamart.api.response.v1.AdjustmentV1Response;
import ru.instamart.api.response.v1.AdjustmentsV1Response;
import ru.instamart.jdbc.dao.stf.SpreeAdjustmentsDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.List;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.*;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;
import static ru.instamart.api.helper.PromotionCode.getPromotionCode;

@Epic("ApiV1")
@Feature("Заказы")
public class AdjustmentsV1Test extends RestBase {

    private OrderV2 order;
    private Long adjustmentId;

    @BeforeClass(alwaysRun = true)
    public void before() {
        SessionFactory.makeSession(SessionType.API_V2);
        UserData userData = SessionFactory.getSession(SessionType.API_V2).getUserData();
        apiV2.dropAndFillCart(userData, EnvironmentProperties.DEFAULT_SID);
        order = apiV2.getOpenOrder();
        apiV2.applyPromoCode(order.getNumber(),getPromotionCode());
        admin.authApi();
    }

    @CaseId(2545)
    @Story("Надбавки к заказу")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение информации о надбавках к заказу")
    public void getAdjustments() {
        final Response response = AdjustmentsV1Request.GET(order.getNumber());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, AdjustmentsV1Response.class);
        List<AdjustmentV1> adjustments = response.as(AdjustmentsV1Response.class).getAdjustments();
        adjustmentId = adjustments.get(0).getId();
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(adjustments.size(), 1, softAssert);
        compareTwoObjects(adjustments.get(0).getSourceType(), "Spree::PromotionAction", softAssert);
        compareTwoObjects(adjustments.get(0).getState(), "open", softAssert);
        softAssert.assertAll();
    }

    @CaseId(2546)
    @Story("Надбавки к заказу")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение информации о надбавках к несуществующему заказу")
    public void getNonExistentAdjustments() {
        final Response response = AdjustmentsV1Request.GET("failedOrderNumber");
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @CaseId(2547)
    @Story("Надбавки к заказу")
    @Test(groups = {"api-instamart-regress"},
            description = "Редактирование информации о надбавке к заказу по id",
            dependsOnMethods = "getAdjustments")
    public void editAdjustment() {
        final Response response = AdjustmentsV1Request.PUT(order.getNumber(), adjustmentId, 200, "Промоакция (ForAutoTest)2");
        checkStatusCode(response, 204);
    }

    @CaseId(2548)
    @Story("Надбавки к заказу")
    @Test(groups = {"api-instamart-regress"},
            description = "Редактирование информации о несуществующей надбавке к заказу по id")
    public void editNonExistentAdjustment() {
        final Response response = AdjustmentsV1Request.PUT(order.getNumber(), 0L, 200, "Промоакция (ForAutoTest)2");
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @CaseId(2549)
    @Story("Надбавки к заказу")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение информации о надбавке к заказу по id",
            dependsOnMethods = "getAdjustments")
    public void getAdjustment() {
        final Response response = AdjustmentsV1Request.GET(order.getNumber(), adjustmentId);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, AdjustmentV1Response.class);
        AdjustmentV1 adjustment = response.as(AdjustmentV1Response.class).getAdjustment();
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(adjustment.getLabel(), "Промоакция (ForAutoTest)2", softAssert);
        compareTwoObjects(adjustment.getSourceType(), "Spree::PromotionAction", softAssert);
        compareTwoObjects(adjustment.getAmount(), 200.0, softAssert);
        compareTwoObjects(adjustment.getState(), "open", softAssert);
        softAssert.assertAll();
    }

    @CaseId(2550)
    @Story("Надбавки к заказу")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение информации о несуществующей надбавке к заказу по id")
    public void getNonExistentAdjustment() {
        final Response response = AdjustmentsV1Request.GET(order.getNumber(), 0L);
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @CaseId(2551)
    @Story("Надбавки к заказу")
    @Test(groups = {"api-instamart-regress"},
            description = "Закрытие надбавок",
            dependsOnMethods = "getAdjustment")
    public void closeAdjustments() {
        final Response response = AdjustmentsV1Request.Close.POST(order.getNumber());
        checkStatusCode(response, 204);
        compareTwoObjects(SpreeAdjustmentsDao.INSTANCE.findById(adjustmentId).get().getState(), "closed");
    }

    @CaseId(2552)
    @Story("Надбавки к заказу")
    @Test(groups = {"api-instamart-regress"},
            description = "Закрытие надбавок несуществующего заказа")
    public void closeNonExistentAdjustments() {
        final Response response = AdjustmentsV1Request.Close.POST("failedOrderNumber");
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @CaseId(2553)
    @Story("Надбавки к заказу")
    @Test(groups = {"api-instamart-regress"},
            description = "Открытие надбавок",
            dependsOnMethods = "closeAdjustments")
    public void openAdjustments() {
        final Response response = AdjustmentsV1Request.Open.POST(order.getNumber());
        checkStatusCode(response, 204);
        compareTwoObjects(SpreeAdjustmentsDao.INSTANCE.findById(adjustmentId).get().getState(), "open");
    }

    @CaseId(2554)
    @Story("Надбавки к заказу")
    @Test(groups = {"api-instamart-regress"},
            description = "Открытие надбавок несуществующего заказа")
    public void openNonExistentAdjustments() {
        final Response response = AdjustmentsV1Request.Open.POST("failedOrderNumber");
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @CaseId(2555)
    @Story("Надбавки к заказу")
    @Test(groups = {"api-instamart-regress"},
            description = "Изменение статуса надбавки",
            dependsOnMethods = "openAdjustments")
    public void switchAdjustment() {
        final Response response = AdjustmentsV1Request.POST(order.getNumber(), adjustmentId);
        checkStatusCode(response, 204);
        compareTwoObjects(SpreeAdjustmentsDao.INSTANCE.findById(adjustmentId).get().getState(), "closed");
    }

    @CaseId(2556)
    @Story("Надбавки к заказу")
    @Test(groups = {"api-instamart-regress"},
            description = "Изменение статуса несуществующей надбавки")
    public void switchNonExistentAdjustment() {
        final Response response = AdjustmentsV1Request.POST(order.getNumber(), 0L);
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @CaseId(2557)
    @Story("Надбавки к заказу")
    @Test(groups = {"api-instamart-regress"},
            description = "Удаление надбавки",
            dependsOnMethods = "switchAdjustment")
    public void deleteAdjustment() {
        final Response response = AdjustmentsV1Request.DELETE(order.getNumber(), adjustmentId);
        checkStatusCode(response, 204);
        Assert.assertTrue(SpreeAdjustmentsDao.INSTANCE.findById(adjustmentId).isEmpty(), "Надбавка не удалилась");
    }

    @CaseId(2558)
    @Story("Надбавки к заказу")
    @Test(groups = {"api-instamart-regress"},
            description = "Удаление несуществующей надбавки")
    public void deleteNonExistentAdjustment() {
        final Response response = AdjustmentsV1Request.DELETE(order.getNumber(), 0L);
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }
}
