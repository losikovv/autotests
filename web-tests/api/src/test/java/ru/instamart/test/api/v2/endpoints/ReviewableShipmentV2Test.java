package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.enums.SessionProvider;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.*;
import ru.instamart.api.request.v2.ReviewableShipmentV2Request;
import ru.instamart.api.request.v2.ShipmentsV2Request;
import ru.instamart.api.response.ErrorResponse;
import ru.instamart.api.response.v2.ReviewableShipmentV2Response;
import ru.instamart.api.response.v2.ShipmentReviewV2Response;
import ru.instamart.jdbc.dao.stf.ShipmentReviewWindowClosesDao;
import ru.instamart.jdbc.dao.stf.SpreeOrdersDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.List;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.*;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;
import static ru.instamart.kraken.util.TimeUtil.getDbDeliveryDateFrom;

@Epic("ApiV2")
@Feature("Отзывы о заказе")
@Slf4j
public class ReviewableShipmentV2Test extends RestBase {
    private String shipmentNumber;
    private UserData userData;
    private OrderV2 order;

    @BeforeMethod(alwaysRun = true)
    public void before() {
        var sid = EnvironmentProperties.DEFAULT_SID;
        SessionFactory.makeSession(SessionType.API_V2, SessionProvider.PHONE);
        userData = SessionFactory.getSession(SessionType.API_V2).getUserData();
        apiV2.dropAndFillCart(userData, sid);
        order = apiV2.getOpenOrder();
        if (order == null) throw new SkipException("Заказ не удалось оплатить");
        shipmentNumber = order.getShipments().get(0).getNumber();
        SpreeOrdersDao.INSTANCE.updateShipmentStateToShip(order.getNumber(), getDbDeliveryDateFrom(0L));
    }

    @CaseIDs(value = {@CaseId(468), @CaseId(1164)})
    @Story("Получение доступных для отправки отзыва подзаказов")
    @Test(groups = {"api-instamart-regress"},
            description = "Автоматическое получение последнего шипмента без оценки при старте приложения. Заказ на аккаунте совершен.")
    public void automaticReceiptLastMessage200() {
        OrderV2 order = apiV2.setDefaultAttributesAndCompleteOrder();
        final Response response = ReviewableShipmentV2Request.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ReviewableShipmentV2Response.class);
        ReviewableShipmentV2 revShipment = response.as(ReviewableShipmentV2Response.class).getReviewableShipment();
        final SoftAssert softAssert = new SoftAssert();
        final ShipmentV2 shipment = order.getShipments().get(0);
        compareTwoObjects(revShipment.getNumber(), shipment.getNumber(), softAssert);
        compareTwoObjects(revShipment.getCost(), shipment.getCost(), softAssert);
        compareTwoObjects(revShipment.getItemTotal(), shipment.getItemTotal(), softAssert);
        compareTwoObjects(revShipment.getItemDiscountTotal(), shipment.getItemDiscountTotal(), softAssert);
        compareTwoObjects(revShipment.getTotal(), shipment.getTotal(), softAssert);
        compareTwoObjects(revShipment.getAlerts(), shipment.getAlerts(), softAssert);
        compareTwoObjects(revShipment.getTotalWeight(), shipment.getTotalWeight(), softAssert);
        compareTwoObjects(revShipment.getItemCount(), shipment.getItemCount(), softAssert);
        compareTwoObjects(revShipment.getPayment(), shipment.getPayment(), softAssert);
        compareTwoObjects(revShipment.getStore(), shipment.getStore(), softAssert);
        softAssert.assertAll();
    }

    @CaseIDs(value = {@CaseId(472), @CaseId(1186)})
    @Story("Создание отзыва о заказе")
    @Test(groups = {"api-instamart-regress"},
            description = "Создание отзыва о заказе с существующим номером",
            dataProvider = "shipmentReviewsData",
            dataProviderClass = RestDataProvider.class)
    public void shipmentsReviewsPositiveRate(Integer rate) {
        ShipmentsV2Request.Review review = ShipmentsV2Request.Review.builder()
                .rate(rate)
                .build();
        final Response response = ShipmentsV2Request.Reviews.POST(shipmentNumber, review);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ShipmentReviewV2Response.class);
        ShipmentReviewV2 shipmentReview = response.as(ShipmentReviewV2Response.class).getShipmentReview();
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(shipmentReview.getRate(), rate, softAssert);
        softAssert.assertNull(shipmentReview.getComment(), "Пришел комментарий");
        compareTwoObjects(shipmentReview.getCallback(), null, softAssert);
        softAssert.assertAll();
    }

    @CaseIDs(value = {@CaseId(1182), @CaseId(1185)})
    @Story("Создание отзыва о заказе")
    @Test(groups = {"api-instamart-regress"},
            description = "Создание отзыва о заказе с существующим номером и галочкой 'Связаться со мной'",
            dataProvider = "shipmentReviewsCallbackData",
            dataProviderClass = RestDataProvider.class)
    public void shipmentsReviewsWithCallBack(Integer rate, Boolean callback) {
        ShipmentsV2Request.Review review = ShipmentsV2Request.Review.builder()
                .rate(rate)
                .callback(callback)
                .build();
        final Response response = ShipmentsV2Request.Reviews.POST(shipmentNumber, review);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ShipmentReviewV2Response.class);
        ShipmentReviewV2 shipmentReview = response.as(ShipmentReviewV2Response.class).getShipmentReview();
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(shipmentReview.getRate(), rate, softAssert);
        softAssert.assertNull(shipmentReview.getComment(), "Пришел комментарий");
        compareTwoObjects(shipmentReview.getCallback(), callback, softAssert);
        softAssert.assertAll();
    }

    @CaseId(475)
    @Story("Создание отзыва о заказе")
    @Test(groups = {"api-instamart-regress"},
            description = "Создание отзыва о заказе с существующим номером и комментарием")
    public void shipmentsReviewsWithComments() {
        ShipmentsV2Request.Review review = ShipmentsV2Request.Review.builder()
                .rate(5)
                .comment("Тестовый комментарий")
                .build();
        final Response response = ShipmentsV2Request.Reviews.POST(shipmentNumber, review);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ShipmentReviewV2Response.class);
        ShipmentReviewV2 shipmentReview = response.as(ShipmentReviewV2Response.class).getShipmentReview();
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(shipmentReview.getRate(), 5, softAssert);
        compareTwoObjects(shipmentReview.getComment(), "Тестовый комментарий", softAssert);
        softAssert.assertAll();
    }

    @CaseId(476)
    @Story("Создание отзыва о заказе")
    @Test(groups = {"api-instamart-regress"},
            description = "Создание отзыва о заказе с несколькими значениями для review[issue_ids]")
    public void shipmentsReviewsWithIds() {
        List<ReviewIssueV2> reviewIssues = apiV2.getReviewIssues(shipmentNumber);

        ShipmentsV2Request.Review review = ShipmentsV2Request.Review.builder()
                .rate(5)
                .issueId(reviewIssues.get(1).getId())
                .issueId(reviewIssues.get(2).getId())
                .build();
        final Response response = ShipmentsV2Request.Reviews.POST(shipmentNumber, review);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ShipmentReviewV2Response.class);
        ShipmentReviewV2 shipmentReview = response.as(ShipmentReviewV2Response.class).getShipmentReview();
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(shipmentReview.getRate(), review.getRate(), softAssert);
        compareTwoObjects(shipmentReview.getComment(), review.getComment(), softAssert);
        compareTwoObjects(shipmentReview.getIssues().get(0).getId(), reviewIssues.get(1).getId(), softAssert);
        compareTwoObjects(shipmentReview.getIssues().get(0).getDescription(), reviewIssues.get(1).getDescription(), softAssert);
        compareTwoObjects(shipmentReview.getIssues().get(0).getCommentNeeded(), reviewIssues.get(1).getCommentNeeded(), softAssert);
        compareTwoObjects(shipmentReview.getIssues().get(1).getId(), reviewIssues.get(2).getId(), softAssert);
        compareTwoObjects(shipmentReview.getIssues().get(1).getDescription(), reviewIssues.get(2).getDescription(), softAssert);
        compareTwoObjects(shipmentReview.getIssues().get(1).getCommentNeeded(), reviewIssues.get(2).getCommentNeeded(), softAssert);
        softAssert.assertAll();
    }

    @CaseId(477)
    @Story("Создание отзыва о заказе")
    @Test(groups = {"api-instamart-regress"},
            description = "Создание отзыва о заказе с [images_attributes]")
    public void shipmentsReviewsWithImage() {
        final Response response = ShipmentsV2Request.Reviews.POST(shipmentNumber);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ShipmentReviewV2Response.class);
        ShipmentReviewV2 shipmentReview = response.as(ShipmentReviewV2Response.class).getShipmentReview();
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(shipmentReview.getRate(), 5, softAssert);
        softAssert.assertTrue(shipmentReview.getImages().get(0).getOriginalUrl().contains("sample.jpg"), "Изображение не добавлено");
        softAssert.assertAll();
    }

    @CaseId(1187)
    @Story("Создание отзыва о заказе")
    @Test(groups = {"api-instamart-regress"},
            description = "Создание отзыва на заказ с уже существующим отзывом")
    public void shipmentsReviewsForSameShipment() {
        ShipmentsV2Request.Review review = ShipmentsV2Request.Review.builder()
                .rate(5)
                .build();
        final Response firstResponse = ShipmentsV2Request.Reviews.POST(shipmentNumber, review);
        checkStatusCode200(firstResponse);
        final Response secondResponse = ShipmentsV2Request.Reviews.POST(shipmentNumber, review);
        checkStatusCode422(secondResponse);
        Assert.assertTrue(secondResponse.asString().contains("\"shipment_id\":\"Для данного заказа уже был добавлен отзыв\""));
    }

    @CaseId(1184)
    @Story("Создание отзыва о заказе")
    @Test(groups = {"api-instamart-regress"},
            description = "Создание отзыва на заказ с токеном другого юзера")
    public void shipmentsReviewsWithAnotherUserToken() {
        ShipmentsV2Request.Review review = ShipmentsV2Request.Review.builder()
                .rate(5)
                .build();
        SessionFactory.makeSession(SessionType.API_V2);
        final Response response = ShipmentsV2Request.Reviews.POST(shipmentNumber, review);
        checkStatusCode403(response);
        checkError(response, "Пользователь не может выполнить это действие");
    }

    @CaseId(1183)
    @Story("Создание отзыва о заказе")
    @Test(groups = {"api-instamart-regress"},
            description = "Создание отзыва без токена")
    public void shipmentsReviewsWithoutToken() {
        ShipmentsV2Request.Review review = ShipmentsV2Request.Review.builder()
                .rate(5)
                .build();
        SessionFactory.clearSession(SessionType.API_V2);
        final Response response = ShipmentsV2Request.Reviews.POST(shipmentNumber, review);
        checkStatusCode401(response);
        checkError(response, "Ключ доступа невалиден или отсутствует");
    }

    @CaseId(1179)
    @Story("Получение последнего подзаказа для отзыва")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение последнего подзаказа для отзыва - Отзыв уже оставлен ранее")
    public void getShipmentReviewWithPreviousReview() {
        OrderV2 order = apiV2.setDefaultAttributesAndCompleteOrder();
        ShipmentsV2Request.Review review = ShipmentsV2Request.Review.builder()
                .rate(5)
                .build();
        final Response postResponse = ShipmentsV2Request.Reviews.POST(order.getShipments().get(0).getNumber(), review);
        checkStatusCode200(postResponse);
        final Response response = ReviewableShipmentV2Request.GET();
        checkStatusCode404(response);
        checkError(response, "ActiveRecord::RecordNotFound");
    }

    @CaseId(1180)
    @Story("Получение последнего подзаказа для отзыва")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение последнего подзаказа для отзыва - Окно с отзывом закрыто дважды")
    public void getShipmentReviewWithTwiceClosedWindows() {
        OrderV2 order = apiV2.setDefaultAttributesAndCompleteOrder();
        for (int i = 0; i < 2; i++) {
            final Response responseCloseWindow = ShipmentsV2Request.ReviewWindowClose.PUT(order.getShipments().get(0).getNumber());
            checkStatusCode(responseCloseWindow, 204);
        }
        final Response response = ReviewableShipmentV2Request.GET();
        checkStatusCode404(response);
        checkError(response, "ActiveRecord::RecordNotFound");
    }

    @CaseId(1162)
    @Story("Закрытие окна заказа")
    @Test(groups = {"api-instamart-regress"},
            description = "Успешное закрытие окна заказа")
    public void closeReviewableWindow() {
        final Response response = ShipmentsV2Request.ReviewWindowClose.PUT(shipmentNumber);
        checkStatusCode(response, 204);
    }

    @CaseId(1823)
    @Story("Закрытие окна заказа")
    @Test(groups = {"api-instamart-regress"},
            description = "Закрытие окна заказа в 256 раз")
    public void closeReviewableWindow255Times() {
        final Response firstCloseResponse = ShipmentsV2Request.ReviewWindowClose.PUT(shipmentNumber);
        checkStatusCode(firstCloseResponse, 204);
        ShipmentReviewWindowClosesDao.INSTANCE.updateNumberOfCloses(255, order.getShipments().get(0).getId());
        final Response response = ShipmentsV2Request.ReviewWindowClose.PUT(shipmentNumber);
        checkStatusCode422(response);
        ErrorResponse error = response.as(ErrorResponse.class);
        compareTwoObjects(error.getErrorMessages().get(0).getMessage(), "может иметь значение меньшее или равное 255");
    }

    @CaseId(1171)
    @Story("Закрытие окна заказа")
    @Test(groups = {"api-instamart-regress"},
            description = "Закрытие окна заказа неавторизованным пользователем")
    public void closeReviewableWindowWithoutAuth() {
        SessionFactory.clearSession(SessionType.API_V2);
        final Response response = ShipmentsV2Request.ReviewWindowClose.PUT(shipmentNumber);
        checkStatusCode401(response);
        checkError(response, "Ключ доступа невалиден или отсутствует");
    }

    @CaseId(1173)
    @Story("Закрытие окна заказа")
    @Test(groups = {"api-instamart-regress"},
            description = "Закрытие окна заказа для несуществующего заказа")
    public void closeReviewableWindowWithAnotherUserToken() {
        SessionFactory.makeSession(SessionType.API_V2);
        final Response response = ShipmentsV2Request.ReviewWindowClose.PUT(shipmentNumber);
        checkStatusCode403(response);
        checkError(response, "Пользователь не может выполнить это действие");
    }
}
