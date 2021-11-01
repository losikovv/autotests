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
import ru.instamart.api.request.v2.ReviewableShipmentV2Request;
import ru.instamart.api.request.v2.ShipmentsV2Request;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.errorAssert;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode404;
import static ru.instamart.api.factory.SessionFactory.createSessionToken;
import static ru.instamart.kraken.data.user.UserManager.getDefaultApiUser;

@Epic("ApiV2")
@Feature("Отзывы о заказе")
@Slf4j
public class ReviewableShipmentWithoutFinishedOrderV2Test extends RestBase {
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

    @CaseId(467)
    @Story("Получение последнего подзаказа без отзыва о заказе")
    @Test(groups = {"api-instamart-regress"},
            description = "Последний подзаказ без оценки но старше 7 дней")
    public void lastSuborderWithoutEvaluationButOlderThan7Days() {
        createSessionToken(SessionType.API_V2_FB, getDefaultApiUser());
        final Response response = ReviewableShipmentV2Request.GET();
        checkStatusCode404(response);
        errorAssert(response, "ActiveRecord::RecordNotFound");
    }


    @CaseId(473)
    @Story("Создание отзыва о заказе")
    @Test(groups = {"api-instamart-regress"},
            description = "Создание отзыва о заказе с несуществующим номером")
    public void test(){
        ShipmentsV2Request.Review review = ShipmentsV2Request.Review.builder()
                .rate(5)
                .build();
        final Response response = ShipmentsV2Request.Reviews.POST("failed920934723904", review);
        checkStatusCode404(response);
        errorAssert(response, "Доставка не существует");
    }
}
