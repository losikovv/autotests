package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.instamart.api.checkpoint.InstamartApiCheckpoints;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.request.v2.ReviewableShipmentV2Request;
import ru.instamart.api.request.v2.ShipmentsV2Request;
import ru.instamart.api.response.v2.ReviewableShipmentV2Response;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.testdata.UserData;

import static org.testng.AssertJUnit.assertEquals;
import static ru.instamart.api.checkpoint.ShopperApiCheckpoints.checkStatusCode200;

@Epic("ApiV2")
@Feature("Отзывы о заказе")
@Slf4j
public class ReviewableShipmentV2Test extends RestBase {
    private String shipmentNumber;
    private UserData userData;

    @BeforeTest(alwaysRun = true)
    public void before() {
        var sid = EnvironmentProperties.DEFAULT_SID;
        SessionFactory.makeSession(SessionType.API_V2_PHONE);
        userData = SessionFactory.getSession(SessionType.API_V2_PHONE).getUserData();
        log.info("user data {}", userData);
        OrderV2 order = apiV2.order(userData, sid);
        if (order == null) throw new SkipException("Заказ не удалось оплатить");
        shipmentNumber = order.getShipments().get(0).getNumber();
        log.info("Shipment number: {}", shipmentNumber);
        String orderNumber = order.getNumber();
        log.info("order number: {}", orderNumber);
        InstamartApiCheckpoints.checkIsDeliveryToday(order);
        //сборка
        shopperApp.simpleCollect(shipmentNumber);
        SessionFactory.createSessionToken(SessionType.API_V2_FB, userData);
        apiV2.waitingForDeliveryStatus(orderNumber);
    }

    @CaseId(468)
    @Issue("STF-8102")
    @Story("Получение последнего подзаказа без отзыва о заказе")
    @Test(enabled = false,
            groups = {"api-instamart-regress"},
            description = "Автоматическое получение последнего шипмента без оценки при старте приложения. Заказ на аккаунте совершен.")
    public void automaticReceiptLastMessage200() {
        SessionFactory.createSessionToken(SessionType.API_V2_FB, userData);
        final Response response = ReviewableShipmentV2Request.GET();
        checkStatusCode200(response);
        ReviewableShipmentV2Response revShipment = response.as(ReviewableShipmentV2Response.class);
        assertEquals(revShipment.getReviewableShipment().getNumber(), shipmentNumber, "Номер доставки не совпадает");
    }


    @CaseId(468)
    @Issue("STF-8102")
    @Story("Создание отзыва о заказе")
    @Test(enabled = false,
            groups = {"api-instamart-regress"},
            description = "Создание отзыва о заказе с существующим номером")
    public void test() {
        ShipmentsV2Request.Review review = ShipmentsV2Request.Review.builder()
                .rate(5)
                .comment("Tests")
                .build();
        final Response response = ShipmentsV2Request.Reviews.POST(shipmentNumber, review);
        response.prettyPeek();
    }
}
