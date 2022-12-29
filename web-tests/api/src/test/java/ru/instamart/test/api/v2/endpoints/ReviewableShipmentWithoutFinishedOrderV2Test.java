package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import ru.instamart.api.response.ErrorResponse;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.enums.Server;
import ru.instamart.kraken.listener.Skip;
import io.qameta.allure.TmsLinks;
import io.qameta.allure.TmsLink;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionProvider;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.v2.ReviewableShipmentV2Request;
import ru.instamart.api.request.v2.ShipmentsV2Request;

import static ru.instamart.api.Group.API_INSTAMART_PROD;
import static ru.instamart.api.Group.API_INSTAMART_REGRESS;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkError;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;
import static ru.instamart.api.factory.SessionFactory.createSessionToken;
import static ru.instamart.kraken.data.user.UserManager.getDefaultApiUser;

@Epic("ApiV2")
@Feature("Отзывы о заказе")
public class ReviewableShipmentWithoutFinishedOrderV2Test extends RestBase {
    @TmsLinks(value = {@TmsLink("466"), @TmsLink("1165")})
    @Story("Получение последнего подзаказа для отзыва")
    @Test(groups = {"api-instamart-smoke", API_INSTAMART_PROD, "api-v2"},
            description = "Получение последнего подзаказа для отзыва - Заказа на аккаунте не было")
    public void automaticReceiptLastMessage404() {
        SessionFactory.makeSession(SessionType.API_V2);
        final Response response = ReviewableShipmentV2Request.GET();
        checkStatusCode404(response);
        checkError(response, "ActiveRecord::RecordNotFound");
    }

    @TmsLink("467")
    @Story("Получение последнего подзаказа для отзыва")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v2"},
            description = "Получение последнего подзаказа для отзыва - Последний подзаказ без оценки но старше 7 дней")
    public void lastSuborderWithoutEvaluationButOlderThan7Days() {
        createSessionToken(SessionType.API_V2, SessionProvider.FB, getDefaultApiUser());
        final Response response = ReviewableShipmentV2Request.GET();
        checkStatusCode404(response);
        checkError(response, "ActiveRecord::RecordNotFound");
    }


    @TmsLink("473")
    @Story("Создание отзыва о заказе")
    @Test(groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v2"},
            description = "Создание отзыва о заказе с несуществующим номером")
    public void shipmentsReviewsForNonexistentOrder(){
        ShipmentsV2Request.Review review = ShipmentsV2Request.Review.builder()
                .rate(5)
                .build();
        final Response response = ShipmentsV2Request.Reviews.POST("failed920934723904", review);
        checkStatusCode404(response);
        checkError(response, "Доставка не существует");
    }

    @TmsLink("1166")
    @Story("Получение последнего подзаказа для отзыва")
    @Test(groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v2"},
            description = "Получение последнего подзаказа для отзыва без авторизации")
    public void getReviewsWithoutAuth(){
        SessionFactory.clearSession(SessionType.API_V2);
        final Response response = ReviewableShipmentV2Request.GET();
        checkStatusCode401(response);
        checkError(response, "Ключ доступа невалиден или отсутствует");
    }

    @Skip(onServer = Server.STAGING) //todo починить на стейдже Index 0 out of bounds for length 0
    @TmsLink("1174")
    @Story("Закрытие окна заказа")
    @Test(groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v2"},
            description = "Закрытие окна заказа для недоставленного заказа")
    public void closeReviewableWindowForNotShippedOrder() {
        SessionFactory.makeSession(SessionType.API_V2);
        apiV2.dropAndFillCart(SessionFactory.getSession(SessionType.API_V2).getUserData(), EnvironmentProperties.DEFAULT_SID);
        final Response response = ShipmentsV2Request.ReviewWindowClose.PUT(apiV2.getShipmentsNumber());
        checkStatusCode422(response);
        ErrorResponse error = response.as(ErrorResponse.class);
        compareTwoObjects(error.getErrorMessages().get(0).getMessage(), "Оценка доступна только для доставленных заказов");
    }

    @TmsLink("1163")
    @Story("Закрытие окна заказа")
    @Test(groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v2"},
            description = "Закрытие окна заказа для несуществующего заказа")
    public void closeReviewableWindowForNonexistentOrder() {
        final Response response = ShipmentsV2Request.ReviewWindowClose.PUT("failedShipmentNumber");
        checkStatusCode404(response);
        checkError(response, "Доставка не существует");
    }
}
