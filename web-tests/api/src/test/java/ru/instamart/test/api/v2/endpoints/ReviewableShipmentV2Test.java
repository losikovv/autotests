package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.request.v2.ReviewableShipmentV2Request;
import ru.instamart.kraken.config.EnvironmentProperties;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.errorAssert;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode404;
import static ru.instamart.api.enums.RailsConsole.Order.*;
import static ru.instamart.api.factory.SessionFactory.createSessionToken;
import static ru.instamart.api.k8s.K8sConsumer.execRailsCommandWithPod;
import static ru.instamart.kraken.testdata.UserManager.getDefaultApiUser;
import static ru.instamart.kraken.util.ThreadUtil.simplyAwait;

@Epic("ApiV2")
@Feature("Отзывы о заказе")
@Slf4j
public class ReviewableShipmentV2Test extends RestBase {

    @CaseId(466)
    @Story("Получение последнего подзаказа без отзыва о заказе")
    @Test(groups = {"api-instamart-regress"},
            description = "Автоматическое получение последнего шипмента без оценки при старте приложения. Заказа на аккаунте не было.")
    public void automaticReceiptLastMessage404() {
        SessionFactory.makeSession(SessionType.API_V2_FB);
        final Response response = ReviewableShipmentV2Request.GET();
        checkStatusCode404(response);
        errorAssert(response, "ActiveRecord::RecordNotFound");
    }

    @CaseId(468)
    @Story("Получение последнего подзаказа без отзыва о заказе")
    @Test(groups = {"api-instamart-regress"},
            description = "Автоматическое получение последнего шипмента без оценки при старте приложения. Заказ на аккаунте совершен.")
    public void automaticReceiptLastMessage200() {
        SessionFactory.makeSession(SessionType.API_V2_PHONE);
        OrderV2 order = apiV2.order(SessionFactory.getSession(SessionType.API_V2_PHONE).getUserData(),
                EnvironmentProperties.DEFAULT_SID);

        String shipmentNumber = order.getShipments().get(0).getNumber();
        log.info("Доставка: " + shipmentNumber);
        String[] command = {
                START_COLLECTING.get(shipmentNumber),
                ASSEMBLY_ITEMS_ORDER.get(shipmentNumber, "0"),
                FINISH_COLLECTING.get(shipmentNumber),
                START_SHIPPING.get(shipmentNumber),
                SHIP.get(shipmentNumber)
        };
        execRailsCommandWithPod(command);
        final Response response = ReviewableShipmentV2Request.GET();
    }


    @CaseId(467)
    @Story("Получение последнего подзаказа без отзыва о заказе")
    @Test(enabled = false, //TODO: заказ оформлен 06.09.2021. Включить после 14.09.2021
            groups = {"api-instamart-regress"},
            description = "Последний подзаказ без оценки но старше 7 дней")
    public void lastSuborderWithoutEvaluationButOlderThan7Days() {
        createSessionToken(SessionType.API_V2_PHONE, getDefaultApiUser());
        final Response response = ReviewableShipmentV2Request.GET();
        checkStatusCode404(response);
        errorAssert(response, "ActiveRecord::RecordNotFound");
    }
}
